/*
	Copyright 2013-2014 Mario Pascucci <mpascucci@gmail.com>
	This file is part of JLDraw.

	JLDraw is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	JLDraw is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with JLDraw.  If not, see <http://www.gnu.org/licenses/>.

*/


package com.hamming.halbo.ldraw;


import it.romabrick.ldrawlib.*;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;


/*
 * Imports in background an LDraw project file
 * @see javax.swing.SwingWorker
 */
public class LDRawParser implements Runnable {

	private String[] ldrawData;
	private String name;
	private LDrawModel mainModel;	// model imported
	private String internalLog = "";		// internal log for errors and warnings
	private boolean warnings = false;

	
	public LDRawParser(String name, String ldrawData) {
		this.name = name;
		this.ldrawData = ldrawData.split(System.getProperty("line.separator"));
	}

	
	public LDrawModel getModel() {
		return mainModel;
	}
	

	private void addLogLine(String file, int line, String message) {
	warnings = true;
		if (line != 0)
			internalLog += "[" +file + "] line# "+ line + "> " + message + "\n";
		else 
			internalLog += "[" +file + "] >" + message + "\n";
			
	}


	@Override
	public void run() {
		int curLine=0;
		String line,part;
		LDrawPart subModel = null;
		LDPrimitive p = null;
		LDrawPartType partType;
		
		// we cheats about complete
		boolean isMpd = false;
		while ((line = ldrawData[curLine++]) != null) {
			LDrawCommand type = LDrawParser.parseCommand(line);
			if (type == LDrawCommand.MPDFILE) {
				isMpd = true;
				break;
			}
		}
		boolean isFirstModel = true;
		boolean isMainModel = false;
		boolean isSubModel = false;
		if (isMpd) {
			// gets all sub-model and custom part in MPD
			// and save as LDrawCustomPart
			boolean firstLine = false;
			partType = LDrawPartType.UNKNOWN;
			while ((line = ldrawData[curLine++]) != null) {
				LDrawCommand type = LDrawParser.parseCommand(line);
				try {
					switch (type) {
					case MPDFILE:
						// if a new FILE command is found without NOFILE first...
						if (isSubModel && !isMainModel) {
							subModel.setPartType(partType);
						}
						isMainModel = false;
						isSubModel = false;
						part = LDrawParser.parseMpdFile(line);
	                    if (isFirstModel) {
	                    	mainModel = LDrawModel.newLDrawModel(part);
	                    	isFirstModel = false;
	                    	isMainModel = true;
	                    }
	                    else {
	                    	isSubModel = true;
		                    if (!LDrawPart.existsCustomPart(part)) {
		                    	subModel = LDrawPart.newCustomPart(part);
		                    }
		                    else {
		                        //------------------- duplicate submodel name
		                    	addLogLine(name, curLine,
		                    			"Duplicate sub-model name '" + part + "' in MPD");   
		                        continue;
		                    }
	                    }
	                    firstLine = true;
	                    if (!isMainModel)
	                    	partType = LDrawPartType.SUBMODEL;
	                    break;
					case MPDNOFILE:
						if (!isSubModel && !isMainModel) {
	                    	addLogLine(name, curLine, 
	                    			"Displaced 'NOFILE' in MPD");   
						}
						if (isSubModel) {
							subModel.setPartType(partType);
						}
						isMainModel = false;
						isSubModel = false;
						break;
					case FILETYPE:
						if (isSubModel && ! isMainModel) {
							subModel.setPartType(LDrawParser.parsePartType(line));
						}
						break;
					case AUTHOR:
						if (isMainModel) {
							mainModel.setAuthor(LDrawParser.parseAuthor(line));
						}
						else {
							subModel.setAuthor(LDrawParser.parseAuthor(line));
						}
						break;
					case NAME:
						if (!isMainModel) {
							subModel.setPartName(LDrawParser.parsePartName(line));
						}
						break;
					case TRIANGLE:
					case LINE:
					case AUXLINE:
					case QUAD:
						partType = LDrawPartType.CUSTOM_PART;
						break;
					case COMMENT:
					case EMPTY:
						break;
					case META_UNKNOWN:
						if (firstLine) {
							if (isMainModel) {
								mainModel.setDescription(LDrawParser.parseDescription(line));
							}
							else if (isSubModel) {
								subModel.setDescription(LDrawParser.parseDescription(line));
							}
							firstLine = false;
						}
						break;
					default:
						if (!isSubModel && !isMainModel) {
							addLogLine(name, curLine, 
	                    			"Invalid MPD file format: primitive or command outside FILE..NOFILE block:"+line );
						}
						break;
					}
				}
				catch (LDrawException exc) {
					addLogLine(name, curLine, exc.getLocalizedMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (isSubModel && ! isMainModel) {
					subModel.setPartType(partType);
				}
			}



			// now read model file
			curLine=0;
			isSubModel = false;
			isFirstModel = true;
			isMainModel = false;
			boolean winding = false;
			boolean invNext = false;
			firstLine = false;
			partType = LDrawPartType.UNKNOWN;
			while ((line = ldrawData[curLine++]) != null) {
				LDrawCommand type = LDrawParser.parseCommand(line);
				//System.out.println(type + "-"+line);
				try {
					switch (type) {
					case MPDFILE:
						// in case FILE command is found without NOFILE first
	                    if (isSubModel) {
	                        //--------- we are in submodel, close and prepare next submodel
	                        isSubModel = false;
	                        if (isMainModel) {
	                        	// end loading main model
	                        	isMainModel = false;
	                        }
	                    }		
						winding = false;
						invNext = false;
						part = LDrawParser.parseMpdFile(line);
						//System.out.println(part);
	                    isSubModel = true;
	                    if (isFirstModel) {
	                    	isFirstModel = false;
	                    	isMainModel = true;
	                    }
	                    else {
	                    	subModel = LDrawPart.getCustomPart(part);
	                    }
	                    firstLine = true;
	                    break;
					case MPDNOFILE:
	                    if (isSubModel) {
	                        //--------- we are in submodel, close and prepare next submodel
	                        isSubModel = false;
	                        if (isMainModel) {
	                        	// end loading main model
	                        	isMainModel = false;
	                        }
	                    }		
	                    break;  // no parts alone admitted in MPD files, so we are always in submodel
					case BFC_CCW:
						winding = false;
						break;
					case BFC_CW:
						winding = true;
						break;
					case BFC_INVERTNEXT:
						invNext = true;
						break;
					case REFERENCE:
						p = LDrawParser.parseLineType1(line,invNext);
						invNext = false;
	                    if (LDrawPart.isLdrPart(p.getId()) || LDrawPart.existsCustomPart(p.getId())) {
	                    	if (isMainModel) {
	    						//System.out.println("MainModel - " +p);
	                    		// add to main model
	                    		mainModel.addPart(LDrawPart.newPlacedPart(p));
	                    	}
	                    	else if (isSubModel) {
	                    		// add to current submodel/custom part
	    						//System.out.println(subModel.getLdrawid() + " - " +p);
	                    		subModel.addPrimitive(p);
	                    	}
	                    	else { 
								addLogLine(name, curLine, 
		                    			"Invalid MPD file format: primitive or command outside FILE..NOFILE block:"+line );
	                    	}
	                    }
	                    else {
                    		addLogLine(subModel.getDescription(), curLine, 
	                        			"Unknown submodel or part: "+p.getId());
	                    }
						break;
					case TRIANGLE: 
						p = LDrawParser.parseLineType3(line,!winding);
                    	if (isMainModel) {
                    		// add to main model
                    		mainModel.addPart(LDrawPart.newPlacedPart(p));
                    	}
                    	else if (isSubModel) {
                    		// add to current submodel/custom part
                    		subModel.addPrimitive(p);
                    	}
                    	break;
					case AUXLINE:
						p = LDrawParser.parseLineType5(line);
                    	if (isMainModel) {
                    		// add to main model
                    		mainModel.addPart(LDrawPart.newPlacedPart(p));
	                    
                    	}
                    	else if (isSubModel) {
                    		// add to current submodel/custom part
                    		subModel.addPrimitive(p);
                    	}
						break;
					case LINE:
						p = LDrawParser.parseLineType2(line);
                    	if (isMainModel) {
                    		// add to main model
                    		mainModel.addPart(LDrawPart.newPlacedPart(p));
	                    
                    	}
                    	else if (isSubModel) {
                    		// add to current submodel/custom part
                    		subModel.addPrimitive(p);
                    	}
						break;
					case QUAD:
						p = LDrawParser.parseLineType4(line,!winding);
                    	if (isMainModel) {
                    		// add to main model
                    		mainModel.addPart(LDrawPart.newPlacedPart(p));
	                    
                    	}
                    	else if (isSubModel) {
                    		// add to current submodel/custom part
                    		subModel.addPrimitive(p);
                    	}
						break;
					default:
						break;
					}
				}
				catch (LDrawException exc) {
					addLogLine(name, curLine, exc.getLocalizedMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {		// it is LDR/DAT format
			mainModel = LDrawModel.newLDrawModel(name);
			boolean winding = false;
			boolean invNext = false;
			boolean firstLine = false;
			while ((line = ldrawData[curLine++]) != null) {
				//System.out.println(line);
				LDrawCommand type = LDrawParser.parseCommand(line);
				try {
					switch (type) {
					case BFC_CCW:
						winding = false;
						break;
					case BFC_CW:
						winding = true;
						break;
					case AUTHOR:
						mainModel.setAuthor(LDrawParser.parseAuthor(line));
						break;
					case BFC_INVERTNEXT:
						invNext = true;
						break;
					case REFERENCE:
						System.out.println("NOT IMPLEMENTED");
	                    break;
					case COLOUR:
						mainModel.addPart(LDrawPart.newPlacedPart(
								LDPrimitive.cmdColour(LDrawParser.parseColour(line))));
						break;						
					case AUXLINE:
						p = LDrawParser.parseLineType5(line);
	               		// add to main model
	               		mainModel.addPart(LDrawPart.newPlacedPart(p));
	                   
						break;
					case LINE:
						p = LDrawParser.parseLineType2(line);
	              		// add to main model
	               		mainModel.addPart(LDrawPart.newPlacedPart(p));
	                   
						break;
					case TRIANGLE: 
						p = LDrawParser.parseLineType3(line,winding);
                   		mainModel.addPart(LDrawPart.newPlacedPart(p));
                    
                    	break;
					case QUAD:
						p = LDrawParser.parseLineType4(line,winding);
                   		mainModel.addPart(LDrawPart.newPlacedPart(p));
                    
						break;
					case META_UNKNOWN:
						if (firstLine) {
							mainModel.setDescription(LDrawParser.parseDescription(line));
							firstLine = false;
						}
						break;
					default:
						break;
					}
				}
				catch (LDrawException exc) {
					addLogLine(name, curLine, exc.getLocalizedMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

