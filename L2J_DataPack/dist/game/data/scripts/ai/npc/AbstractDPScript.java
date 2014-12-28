package ai.npc;
/*
 * Copyright (C) 2004-2014 L2J DataPack
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
import java.io.File;
import java.util.logging.Level;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.datatables.MultisellData;
import com.l2jserver.gameserver.engines.DocumentParser;
import com.l2jserver.gameserver.model.items.L2Item;
import com.l2jserver.util.PropertiesParser;

/**
 * @author UnAfraid
 */
public abstract class AbstractDPScript extends AbstractNpcAI
{
	private ConfigurationParser _parser = null;
	private PropertiesParser _properties = null;
	
	public AbstractDPScript(String name, String descr)
	{
		super(name, descr);
	}
	
	protected void load()
	{
	}
	
	protected void loadProperties(File file)
	{
		if (_properties == null)
		{
			_properties = new PropertiesParser(file);
		}
	}
	
	protected String getProperty(String key, String defaultValue)
	{
		if (_properties == null)
		{
			return defaultValue;
		}
		return _properties.getString(key, defaultValue);
	}
	
	protected PropertiesParser getProperties()
	{
		return _properties;
	}
	
	/**
	 * This method is called when using parseFile, parseDirectory methods.<br>
	 * Containing XML {@link Document} with all the data from specified file/folder.
	 */
	protected void parseDocument()
	{
	}
	
	/**
	 * This method is called when using parseFile, parseDirectory methods.<br>
	 * Containing XML {@link Document} with all the data from specified file/folder.
	 * @param doc
	 */
	protected void parseDocument(Document doc)
	{
	}
	
	private final class ConfigurationParser extends DocumentParser
	{
		protected ConfigurationParser()
		{
		}
		
		@Override
		public void load()
		{
		}
		
		@Override
		protected Document getCurrentDocument()
		{
			return super.getCurrentDocument();
		}
		
		@Override
		protected void parseFile(File f)
		{
			super.parseFile(f);
		}
		
		@Override
		protected boolean parseDirectory(File dir, boolean recursive)
		{
			return super.parseDirectory(dir, recursive);
		}
		
		@Override
		protected boolean parseDirectory(String path)
		{
			return super.parseDirectory(path);
		}
		
		@Override
		protected boolean parseDirectory(String path, boolean recursive)
		{
			return super.parseDirectory(path, recursive);
		}
		
		@Override
		protected void parseDocument()
		{
			AbstractDPScript.this.parseDocument();
			AbstractDPScript.this.parseDocument(getCurrentDocument());
		}
		
		@Override
		protected void parseDatapackFile(String path)
		{
			super.parseDatapackFile(path);
		}
	}
	
	/**
	 * Gets the current file.
	 * @return the current file
	 */
	protected final File getCurrentFile()
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		return _parser.getCurrentFile();
	}
	
	/**
	 * Gets the current document.
	 * @return the current document
	 */
	protected final Document getCurrentDocument()
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		return _parser.getCurrentDocument();
	}
	
	/**
	 * Wrapper for {@link #parseFile(File)} method.
	 * @param path the relative path to the datapack root of the XML file to parse.
	 */
	protected final void parseDatapackFile(String path)
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		_parser.parseDatapackFile(path);
	}
	
	/**
	 * Parses a single XML file.<br>
	 * If the file was successfully parsed, call {@link #parseDocument(Document)} for the parsed document.<br>
	 * <b>Validation is enforced.</b>
	 * @param f the XML file to parse.
	 */
	protected final void parseFile(File f)
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		_parser.parseFile(f);
	}
	
	/**
	 * Wrapper for {@link #parseDirectory(File, boolean)}.
	 * @param file the path to the directory where the XML files are.
	 * @return {@code false} if it fails to find the directory, {@code true} otherwise.
	 */
	protected final boolean parseDirectory(File file)
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		return _parser.parseDirectory(file, false);
	}
	
	/**
	 * Wrapper for {@link #parseDirectory(File, boolean)}.
	 * @param path the path to the directory where the XML files are.
	 * @return {@code false} if it fails to find the directory, {@code true} otherwise.
	 */
	protected final boolean parseDirectory(String path)
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		return _parser.parseDirectory(path);
	}
	
	/**
	 * Wrapper for {@link #parseDirectory(File, boolean)}.
	 * @param path the path to the directory where the XML files are.
	 * @param recursive parses all sub folders if there is.
	 * @return {@code false} if it fails to find the directory, {@code true} otherwise.
	 */
	protected final boolean parseDirectory(String path, boolean recursive)
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		return _parser.parseDirectory(path, recursive);
	}
	
	/**
	 * Loads all XML files from {@code path} and calls {@link #parseFile(File)} for each one of them.
	 * @param dir the directory object to scan.
	 * @param recursive parses all sub folders if there is.
	 * @return {@code false} if it fails to find the directory, {@code true} otherwise.
	 */
	protected final boolean parseDirectory(File dir, boolean recursive)
	{
		if (_parser == null)
		{
			_parser = new ConfigurationParser();
		}
		return _parser.parseDirectory(dir, recursive);
	}
	
	/**
	 * Parses a boolean value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Boolean parseBoolean(Node node, Boolean defaultValue)
	{
		return node != null ? Boolean.valueOf(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses a boolean value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Boolean parseBoolean(Node node)
	{
		return parseBoolean(node, null);
	}
	
	/**
	 * Parses a boolean value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Boolean parseBoolean(NamedNodeMap attrs, String name)
	{
		return parseBoolean(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses a boolean value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Boolean parseBoolean(NamedNodeMap attrs, String name, Boolean defaultValue)
	{
		return parseBoolean(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses a byte value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Byte parseByte(Node node, Byte defaultValue)
	{
		return node != null ? Byte.valueOf(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses a byte value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Byte parseByte(Node node)
	{
		return parseByte(node, null);
	}
	
	/**
	 * Parses a byte value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Byte parseByte(NamedNodeMap attrs, String name)
	{
		return parseByte(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses a byte value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Byte parseByte(NamedNodeMap attrs, String name, Byte defaultValue)
	{
		return parseByte(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses a short value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Short parseShort(Node node, Short defaultValue)
	{
		return node != null ? Short.valueOf(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses a short value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Short parseShort(Node node)
	{
		return parseShort(node, null);
	}
	
	/**
	 * Parses a short value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Short parseShort(NamedNodeMap attrs, String name)
	{
		return parseShort(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses a short value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Short parseShort(NamedNodeMap attrs, String name, Short defaultValue)
	{
		return parseShort(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses an int value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected int parseInt(Node node, Integer defaultValue)
	{
		return node != null ? Integer.parseInt(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses an int value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected int parseInt(Node node)
	{
		return parseInt(node, -1);
	}
	
	/**
	 * Parses a int value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected int parseInt(NamedNodeMap attrs, String name, int defaultValue)
	{
		return parseInt(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses a int value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected int parseInt(NamedNodeMap attrs, String name)
	{
		return parseInt(attrs.getNamedItem(name), -1);
	}
	
	/**
	 * Parses an integer value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Integer parseInteger(Node node, Integer defaultValue)
	{
		return node != null ? Integer.valueOf(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses an integer value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Integer parseInteger(Node node)
	{
		return parseInteger(node, null);
	}
	
	/**
	 * Parses an integer value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Integer parseInteger(NamedNodeMap attrs, String name)
	{
		return parseInteger(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses an integer value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Integer parseInteger(NamedNodeMap attrs, String name, Integer defaultValue)
	{
		return parseInteger(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses a long value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Long parseLong(Node node, Long defaultValue)
	{
		return node != null ? Long.valueOf(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses a long value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Long parseLong(Node node)
	{
		return parseLong(node, null);
	}
	
	/**
	 * Parses a long value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Long parseLong(NamedNodeMap attrs, String name)
	{
		return parseLong(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses a long value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Long parseLong(NamedNodeMap attrs, String name, Long defaultValue)
	{
		return parseLong(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses a float value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Float parseFloat(Node node, Float defaultValue)
	{
		return node != null ? Float.valueOf(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses a float value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Float parseFloat(Node node)
	{
		return parseFloat(node, null);
	}
	
	/**
	 * Parses a float value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Float parseFloat(NamedNodeMap attrs, String name)
	{
		return parseFloat(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses a float value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Float parseFloat(NamedNodeMap attrs, String name, Float defaultValue)
	{
		return parseFloat(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses a double value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Double parseDouble(Node node, Double defaultValue)
	{
		return node != null ? Double.valueOf(node.getNodeValue()) : defaultValue;
	}
	
	/**
	 * Parses a double value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Double parseDouble(Node node)
	{
		return parseDouble(node, null);
	}
	
	/**
	 * Parses a double value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected Double parseDouble(NamedNodeMap attrs, String name)
	{
		return parseDouble(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses a double value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected Double parseDouble(NamedNodeMap attrs, String name, Double defaultValue)
	{
		return parseDouble(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses a string value.
	 * @param node the node to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected String parseString(Node node, String defaultValue)
	{
		return node != null ? node.getNodeValue() : defaultValue;
	}
	
	/**
	 * Parses a string value.
	 * @param node the node to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected String parseString(Node node)
	{
		return parseString(node, null);
	}
	
	/**
	 * Parses a string value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @return if the node is not null, the value of the parsed node, otherwise null
	 */
	protected String parseString(NamedNodeMap attrs, String name)
	{
		return parseString(attrs.getNamedItem(name));
	}
	
	/**
	 * Parses a string value.
	 * @param attrs the attributes
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null, the value of the parsed node, otherwise the default value
	 */
	protected String parseString(NamedNodeMap attrs, String name, String defaultValue)
	{
		return parseString(attrs.getNamedItem(name), defaultValue);
	}
	
	/**
	 * Parses an enumerated value.
	 * @param <T> the enumerated type
	 * @param node the node to parse
	 * @param clazz the class of the enumerated
	 * @param defaultValue the default value
	 * @return if the node is not null and the node value is valid the parsed value, otherwise the default value
	 */
	protected <T extends Enum<T>> T parseEnum(Node node, Class<T> clazz, T defaultValue)
	{
		if (node == null)
		{
			return defaultValue;
		}
		
		try
		{
			return Enum.valueOf(clazz, node.getNodeValue());
		}
		catch (IllegalArgumentException e)
		{
			_log.warning("[" + getCurrentFile().getName() + "] Invalid value specified for node: " + node.getNodeName() + " specified value: " + node.getNodeValue() + " should be enum value of \"" + clazz.getSimpleName() + "\" using default value: " + defaultValue);
			return defaultValue;
		}
	}
	
	/**
	 * Parses an enumerated value.
	 * @param <T> the enumerated type
	 * @param node the node to parse
	 * @param clazz the class of the enumerated
	 * @return if the node is not null and the node value is valid the parsed value, otherwise null
	 */
	protected <T extends Enum<T>> T parseEnum(Node node, Class<T> clazz)
	{
		return parseEnum(node, clazz, null);
	}
	
	/**
	 * Parses an enumerated value.
	 * @param <T> the enumerated type
	 * @param attrs the attributes
	 * @param clazz the class of the enumerated
	 * @param name the name of the attribute to parse
	 * @return if the node is not null and the node value is valid the parsed value, otherwise null
	 */
	protected <T extends Enum<T>> T parseEnum(NamedNodeMap attrs, Class<T> clazz, String name)
	{
		return parseEnum(attrs.getNamedItem(name), clazz);
	}
	
	/**
	 * Parses an enumerated value.
	 * @param <T> the enumerated type
	 * @param attrs the attributes
	 * @param clazz the class of the enumerated
	 * @param name the name of the attribute to parse
	 * @param defaultValue the default value
	 * @return if the node is not null and the node value is valid the parsed value, otherwise the default value
	 */
	protected <T extends Enum<T>> T parseEnum(NamedNodeMap attrs, Class<T> clazz, String name, T defaultValue)
	{
		return parseEnum(attrs.getNamedItem(name), clazz, defaultValue);
	}
	
	protected final String getAbsolutePath()
	{
		String path = getClass().getName();
		path = path.replace('.', '/');
		path = path.substring(0, path.lastIndexOf('/'));
		path = "data/scripts/" + path;
		return path;
	}
	
	@Override
	public String getHtm(String prefix, String fileName)
	{
		final String content = HtmCache.getInstance().getHtm(prefix, getAbsolutePath() + "/" + fileName);
		if (content == null)
		{
			log(Level.WARNING, "Missing html: " + fileName + " on " + getAbsolutePath() + " !", new IllegalStateException());
		}
		return content;
	}
	
	protected final void log(String text)
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": " + text);
	}
	
	protected final void log(Level level, String text)
	{
		_log.log(level, getClass().getSimpleName() + ": " + text);
	}
	
	protected final void log(Level level, String text, Exception e)
	{
		_log.log(level, getClass().getSimpleName() + ": " + text, e);
	}
	
	protected String getItemName(int itemId)
	{
		final L2Item item = ItemTable.getInstance().getTemplate(itemId);
		if (item != null)
		{
			return item.getName();
		}
		switch (itemId)
		{
			case MultisellData.PC_BANG_POINTS:
			{
				return "Player Commendation Points";
			}
			case MultisellData.CLAN_REPUTATION:
			{
				return "Clan Reputation Points";
			}
			case MultisellData.FAME:
			{
				return "Fame";
			}
		}
		return "N/A";
	}
	
	protected String getItemIcon(int itemId)
	{
		final L2Item item = ItemTable.getInstance().getTemplate(itemId);
		if (item != null)
		{
			return item.getIcon();
		}
		switch (itemId)
		{
			case MultisellData.PC_BANG_POINTS:
			{
				return "icon.etc_pccafe_point_i00";
			}
			case MultisellData.CLAN_REPUTATION:
			{
				return "icon.etc_bloodpledge_point_i00";
			}
			case MultisellData.FAME:
			{
				return "icon.pvp_point_i00";
			}
		}
		return "N/A";
	}
}