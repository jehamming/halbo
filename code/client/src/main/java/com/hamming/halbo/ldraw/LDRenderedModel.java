/*
	Copyright 2014 Mario Pascucci <mpascucci@gmail.com>
	This file is part of JLDraw

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


import it.romabrick.ldrawlib.LDrawModel;
import it.romabrick.ldrawlib.LDrawPart;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * LDraw model rendered in OpenGL-ready vertex array objects
 * 
 * @author Mario Pascucci
 *
 */
public class LDRenderedModel {
	
	private LDrawModel mainModel;
	private Map<Integer,LDRenderedPart> parts = new HashMap<Integer,LDRenderedPart>();
	private boolean completed;
	
	
	private LDRenderedModel(LDrawModel model) {
		
		mainModel = model;
	}
	
	
	
	/**
	 * WARNING! May be a LOOOOONG task
	 * @throws IOException 
	 */
	public void render() throws IOException {
		
		parts.clear();
		// for every part in model
		for (LDrawPart p : mainModel.getPartList()) {
			// it is in rendered part cache?
			LDRenderedPart pp = LDRenderedPart.getByGlobalId(p.getId());
			if (pp != null) {
				// part exists already rendered
				parts.put(pp.getId(),pp);
			}
			else {
				// render part
				pp = LDRenderedPart.newRenderedPart(p);
				parts.put(pp.getId(),pp);
			}
		}
	}
	
	
	
	public static LDRenderedModel newLDRenderedModel(LDrawModel m) {
		
		LDRenderedModel model = new LDRenderedModel(m);
		// place model to OpenGL coordinate origin
		model.parts = new HashMap<Integer,LDRenderedPart>();
		return model;
	}

	
	public Collection<LDRenderedPart> getParts() {
		return parts.values();
	}






}
