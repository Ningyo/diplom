/*******************************************************************************
 * Copyright 2016
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.lmf.model.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests methods of {@link LexicalEntry} class
 * 
 * @author Zijad Maksuti
 *
 */
public class LexicalEntryTest {
	
	private LexicalEntry lexicalEntry;

	@Before
	public void setUp(){
		lexicalEntry = new LexicalEntry("l");
	}
	
	/**
	 * Tests {@link #LexicalEntry(String)}
	 */
	@Test
	public void testLexicalEntry(){
		String id = "2";
		lexicalEntry = new LexicalEntry(id);
		assertEquals(id, lexicalEntry.getId());
	}

}
