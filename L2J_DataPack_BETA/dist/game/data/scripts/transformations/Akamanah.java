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
package transformations;

import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Akamanah extends L2Transformation
{
	public Akamanah()
	{
		// TODO: Unhardcode Akamanah and Zariche transformations as much as we can
		// id, colRadius, colHeight
		super(302, 10, 32.73);
	}
	
	@Override
	public void onTransform()
	{
		// Set charachter name to transformed name
		getPlayer().getAppearance().setVisibleName("Akamanah");
		getPlayer().getAppearance().setVisibleTitle("");
	}
	
	@Override
	public void onUntransform()
	{
		// set character back to true name.
		getPlayer().getAppearance().setVisibleName(null);
		getPlayer().getAppearance().setVisibleTitle(null);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Akamanah());
	}
}
