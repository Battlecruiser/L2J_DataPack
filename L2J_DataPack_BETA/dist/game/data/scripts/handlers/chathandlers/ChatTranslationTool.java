/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.chathandlers;

import java.util.Arrays;
import java.util.List;

import com.l2jserver.util.Rnd;

/**
 * Translation tool.
 * @author Zoey76
 */
public class ChatTranslationTool
{
	private static final List<Character> TRANSLATION_TABLE = Arrays.asList('@', '8', '(', '9', '3', 'F', '6', 'h', '|', 'j', 'k', 'L', 'm', 'n', '0', 'p', 'q', 'R', '$', '7', 'u', 'v', 'W', '%', 'y', 'z');
	
	protected static String traslate(String text)
	{
		final StringBuilder result = new StringBuilder(text.length() + 10);
		Character x;
		for (char c : text.toCharArray())
		{
			x = getChar(c);
			if (Character.isLetter(x) && Rnd.nextBoolean())
			{
				x = Character.isUpperCase(x) ? Character.toLowerCase(x) : Character.toUpperCase(x);
			}
			result.append(x);
		}
		return result.toString();
	}
	
	private static final Character getChar(char c)
	{
		int index = -1;
		if ((c > 96) && (c < 123))
		{
			index = c - 97;
		}
		else if ((c > 64) && (c < 91))
		{
			index = c - 65;
		}
		return index < 0 ? c : TRANSLATION_TABLE.get(index);
	}
}