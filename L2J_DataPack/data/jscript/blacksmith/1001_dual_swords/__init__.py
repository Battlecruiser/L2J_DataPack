import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SMITHS = [7283,7298,7300,7317,7458,7471,7526,7527,7536,7621,7678,7688,7846,7898,8002,8044,8271,8274,8316,8539,8583,8626,8668]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

# Saber*Saber
    if event == "1":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(1458) >= 145:
            st.takeItems(123,1)
            if st.getQuestItemsCount(123) >= 1:
               st.takeItems(123,1)
               st.takeItems(1458,145)
               st.giveItems(2516,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Bastard Sword
    if event == "2":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(1458) >= 25:
            st.takeItems(123,1)
            if st.getQuestItemsCount(69) >= 1:
               st.takeItems(69,1)
               st.takeItems(1458,25)
               st.giveItems(2517,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Spinebone Sword
    if event == "3":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(1458) >= 25:
            st.takeItems(123,1)
            if st.getQuestItemsCount(125) >= 1:
               st.takeItems(125,1)
               st.takeItems(1458,25)
               st.giveItems(2518,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Artisan's Sword
    if event == "4":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(1458) >= 25:
            st.takeItems(123,1)
            if st.getQuestItemsCount(126) >= 1:
               st.takeItems(126,1)
               st.takeItems(1458,25)
               st.giveItems(2519,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Knight's Sword
    if event == "5":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(1458) >= 25:
            st.takeItems(123,1)
            if st.getQuestItemsCount(128) >= 1:
               st.takeItems(128,1)
               st.takeItems(1458,25)
               st.giveItems(2520,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Crimson Sword
    if event == "6":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(1458) >= 51:
            st.takeItems(123,1)
            if st.getQuestItemsCount(127) >= 1:
               st.takeItems(127,1)
               st.takeItems(1458,51)
               st.giveItems(2521,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Elven Sword
    if event == "7":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(1458) >= 51:
            st.takeItems(123,1)
            if st.getQuestItemsCount(130) >= 1:
               st.takeItems(130,1)
               st.takeItems(1458,51)
               st.giveItems(2522,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Sword of Revolution
    if event == "8":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 362:
            st.takeItems(123,1)
            if st.getQuestItemsCount(129) >= 1:
               st.takeItems(129,1)
               st.takeItems(1458,362)
               st.giveItems(2523,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Saber*Elven Long Sword
    if event == "9":
         if st.getQuestItemsCount(123) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 205:
            st.takeItems(123,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,205)
               st.giveItems(2524,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(123,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Bastard Sword
    if event == "10":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(1458) >= 286:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(69) >= 1:
                st.takeItems(69,1)
                st.takeItems(1458,286)
		st.giveItems(2525,1)
		htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Spinebone Sword
    if event == "11":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(1458) >= 286:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(125) >= 1:
                st.takeItems(125,1)
                st.takeItems(1458,286)
                st.giveItems(2526,1)
                htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Artisan's Sword
    if event == "12":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(1458) >= 286:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(126) >= 1:
                st.takeItems(126,1)
                st.takeItems(1458,286)
                st.giveItems(2527,1)
                htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Knight's Sword
    if event == "13":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(1458) >= 286:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(128) >= 1:
                st.takeItems(128,1)
                st.takeItems(1458,286)
                st.giveItems(2528,1)
                htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Crimson Sword
    if event == "14":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(1458) >= 117:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(127) >= 1:
                st.takeItems(127,1)
                st.takeItems(1458,117)
                st.giveItems(2529,1)
                htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Elven Sword
    if event == "15":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(1458) >= 117:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(130) >= 1:
                st.takeItems(130,1)
                st.takeItems(1458,117)
                st.giveItems(2530,1)
                htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Sword of Revolution
    if event == "16":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 185:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(129) >= 1:
                st.takeItems(129,1)
                st.takeItems(1458,185)
                st.giveItems(2531,1)
                htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Bastard Sword*Elven Long Sword
    if event == "17":
         if st.getQuestItemsCount(69) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 75:
	     st.takeItems(69,1)
	     if st.getQuestItemsCount(2499) >= 1:
                st.takeItems(2499,1)
                st.takeItems(1458,75)
                st.giveItems(2532,1)
                htmltext = "Item has been succesfully created."
             else:
		st.giveItems(69,1)
		htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spinebone Sword*Spinebone Sword
    if event == "18":
         if st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(1458) >= 286:
            st.takeItems(125,1)
            if st.getQuestItemsCount(125) >= 1:
               st.takeItems(125,1)
               st.takeItems(1458,286)
               st.giveItems(2533,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(125,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spinebone Sword*Artisan's Sword
    if event == "19":
         if st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(1458) >= 286:
            st.takeItems(125,1)
            if st.getQuestItemsCount(126) >= 1:
               st.takeItems(126,1)
               st.takeItems(1458,286)
               st.giveItems(2534,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(125,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spinebone Sword*Knight's Sword
    if event == "20":
         if st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(1458) >= 286:
            st.takeItems(125,1)
            if st.getQuestItemsCount(128) >= 1:
               st.takeItems(128,1)
               st.takeItems(1458,286)
               st.giveItems(2535,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(125,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spinebone Sword*Crimson Sword
    if event == "21":
         if st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(1458) >= 117:
            st.takeItems(125,1)
            if st.getQuestItemsCount(127) >= 1:
               st.takeItems(127,1)
               st.takeItems(1458,117)
               st.giveItems(2536,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(125,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spinebone Sword*Elven Sword
    if event == "22":
         if st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(1458) >= 117:
            st.takeItems(125,1)
            if st.getQuestItemsCount(130) >= 1:
               st.takeItems(130,1)
               st.takeItems(1458,117)
               st.giveItems(2537,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(125,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spinebone Sword*Sword of Revolution
    if event == "23":
         if st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 185:
            st.takeItems(125,1)
            if st.getQuestItemsCount(129) >= 1:
               st.takeItems(129,1)
               st.takeItems(1458,185)
               st.giveItems(2538,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(125,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spinebone Sword*Elven Long Sword
    if event == "24":
         if st.getQuestItemsCount(125) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 75:
            st.takeItems(125,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,75)
               st.giveItems(2539,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(125,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Artisan's Sword*Artisan's Sword
    if event == "25":
         if st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(1458) >= 268:
            st.takeItems(126,1)
            if st.getQuestItemsCount(126) >= 1:
               st.takeItems(126,1)
               st.takeItems(1458,268)
               st.giveItems(2540,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(126,1)
               htmltext = "You need one more sword."
  
         else:
             htmltext = "You do not have enough materials."

# Artisan's Sword*Knight's Sword
    if event == "26":
         if st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(1458) >= 268:
            st.takeItems(126,1)
            if st.getQuestItemsCount(128) >= 1:
               st.takeItems(128,1)
               st.takeItems(1458,268)
               st.giveItems(2541,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(126,1)
               htmltext = "You need one more sword."
  
         else:
             htmltext = "You do not have enough materials."

# Artisan's Sword*Crimson Sword
    if event == "27":
         if st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(1458) >= 117:
            st.takeItems(126,1)
            if st.getQuestItemsCount(127) >= 1:
               st.takeItems(127,1)
               st.takeItems(1458,117)
               st.giveItems(2542,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(126,1)
               htmltext = "You need one more sword."
  
         else:
             htmltext = "You do not have enough materials."

# Artisan's Sword*Elven Sword
    if event == "28":
         if st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(1458) >= 117:
            st.takeItems(126,1)
            if st.getQuestItemsCount(130) >= 1:
               st.takeItems(130,1)
               st.takeItems(1458,117)
               st.giveItems(2543,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(126,1)
               htmltext = "You need one more sword."
  
         else:
             htmltext = "You do not have enough materials."

# Artisan's Sword*Sword of Revolution
    if event == "29":
         if st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 185:
            st.takeItems(126,1)
            if st.getQuestItemsCount(129) >= 1:
               st.takeItems(129,1)
               st.takeItems(1458,185)
               st.giveItems(2544,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(126,1)
               htmltext = "You need one more sword."
  
         else:
             htmltext = "You do not have enough materials."

# Artisan's Sword*Elven Long Sword
    if event == "30":
         if st.getQuestItemsCount(126) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 75:
            st.takeItems(126,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,75)
               st.giveItems(2545,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(126,1)
               htmltext = "You need one more sword."
  
         else:
             htmltext = "You do not have enough materials."

# Knight's Sword*Knight's Sword
    if event == "31":
         if st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(1458) >= 268:
            st.takeItems(128,1)
            if st.getQuestItemsCount(128) >= 1:
               st.takeItems(128,1)
               st.takeItems(1458,268)
               st.giveItems(2546,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(128,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Knight's Sword*Crimson Sword
    if event == "32":
         if st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(1458) >= 117:
            st.takeItems(128,1)
            if st.getQuestItemsCount(127) >= 1:
               st.takeItems(127,1)
               st.takeItems(1458,117)
               st.giveItems(2547,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(128,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Knight's Sword*Elven Sword
    if event == "33":
         if st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(1458) >= 117:
            st.takeItems(128,1)
            if st.getQuestItemsCount(130) >= 1:
               st.takeItems(130,1)
               st.takeItems(1458,117)
               st.giveItems(2548,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(128,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Knight's Sword*Sword of Revolution
    if event == "34":
         if st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 185:
            st.takeItems(128,1)
            if st.getQuestItemsCount(129) >= 1:
               st.takeItems(129,1)
               st.takeItems(1458,185)
               st.giveItems(2549,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(128,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Knight's Sword*Elven Long Sword
    if event == "35":
         if st.getQuestItemsCount(128) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 75:
            st.takeItems(128,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,75)
               st.giveItems(2550,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(128,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Crimson Sword*Crimson Sword
    if event == "36":
         if st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(1458) >= 54:
            st.takeItems(127,1)
            if st.getQuestItemsCount(127) >= 1:
               st.takeItems(127,1)
               st.takeItems(1458,54)
               st.giveItems(2551,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(127,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Crimson Sword*Elven Sword
    if event == "37":
         if st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(1458) >= 54:
            st.takeItems(127,1)
            if st.getQuestItemsCount(130) >= 1:
               st.takeItems(130,1)
               st.takeItems(1458,54)
               st.giveItems(2552,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(127,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Crimson Sword*Sword of Revolution
    if event == "38":
         if st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 265:
            st.takeItems(127,1)
            if st.getQuestItemsCount(129) >= 1:
               st.takeItems(129,1)
               st.takeItems(1458,265)
               st.giveItems(2553,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(127,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Crimson Sword*Elven Long Sword
    if event == "39":
         if st.getQuestItemsCount(127) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 260:
            st.takeItems(127,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,260)
               st.giveItems(2554,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(127,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Elven Sword*Elven Sword
    if event == "40":
         if st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(1458) >= 54:
            st.takeItems(130,1)
            if st.getQuestItemsCount(130) >= 1:
               st.takeItems(130,1)
               st.takeItems(1458,54)
               st.giveItems(2555,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(127,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Elven Sword*Sword of Revolution
    if event == "41":
         if st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 265:
            st.takeItems(130,1)
            if st.getQuestItemsCount(129) >= 1:
               st.takeItems(129,1)
               st.takeItems(1458,265)
               st.giveItems(2556,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(127,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Elven Sword*Elven Long Sword
    if event == "42":
         if st.getQuestItemsCount(130) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 260:
            st.takeItems(130,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,260)
               st.giveItems(2557,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(127,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Sword of Revolution*Sword of Revolution
    if event == "43":
         if st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(1458) >= 178:
            st.takeItems(129,1)
            if st.getQuestItemsCount(129) >= 1:
               st.takeItems(129,1)
               st.takeItems(1458,178)
               st.giveItems(2558,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(129,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Sword of Revolution*Elven Long Sword
    if event == "44":
         if st.getQuestItemsCount(129) >= 1 and st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 354:
            st.takeItems(129,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,354)
               st.giveItems(2559,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(129,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Elven Long Sword*Elven Long Sword
    if event == "45":
         if st.getQuestItemsCount(2499) >= 1 and st.getQuestItemsCount(1458) >= 535:
            st.takeItems(2499,1)
            if st.getQuestItemsCount(2499) >= 1:
               st.takeItems(2499,1)
               st.takeItems(1458,535)
               st.giveItems(2560,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(2499,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Stormbringer*Stormbringer
    if event == "46":
         if st.getQuestItemsCount(72) >= 1 and st.getQuestItemsCount(1458) >= 495:
            st.takeItems(72,1)
            if st.getQuestItemsCount(72) >= 1:
               st.takeItems(72,1)
               st.takeItems(1458,495)
               st.giveItems(2561,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(72,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Stormbringer*Shamshir
    if event == "47":
         if st.getQuestItemsCount(72) >= 1 and st.getQuestItemsCount(73) >= 1 and st.getQuestItemsCount(1458) >= 285:
            st.takeItems(72,1)
            if st.getQuestItemsCount(73) >= 1:
               st.takeItems(73,1)
               st.takeItems(1458,285)
               st.giveItems(2562,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(72,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Stormbringer*Katana
    if event == "48":
         if st.getQuestItemsCount(72) >= 1 and st.getQuestItemsCount(74) >= 1 and st.getQuestItemsCount(1458) >= 285:
            st.takeItems(72,1)
            if st.getQuestItemsCount(74) >= 1:
               st.takeItems(74,1)
               st.takeItems(1458,285)
               st.giveItems(2563,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(72,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Stormbringer*Spirits Sword
    if event == "49":
         if st.getQuestItemsCount(72) >= 1 and st.getQuestItemsCount(131) >= 1 and st.getQuestItemsCount(1458) >= 285:
            st.takeItems(72,1)
            if st.getQuestItemsCount(131) >= 1:
               st.takeItems(131,1)
               st.takeItems(1458,285)
               st.giveItems(2564,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(72,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Stormbringer*Raid Sword
    if event == "50":
         if st.getQuestItemsCount(72) >= 1 and st.getQuestItemsCount(133) >= 1 and st.getQuestItemsCount(1458) >= 285:
            st.takeItems(72,1)
            if st.getQuestItemsCount(133) >= 1:
               st.takeItems(133,1)
               st.takeItems(1458,285)
               st.giveItems(2565,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(72,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Shamshir*Shamshir
    if event == "51":
         if st.getQuestItemsCount(73) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(73,1)
            if st.getQuestItemsCount(73) >= 1:
               st.takeItems(73,1)
               st.takeItems(1458,954)
               st.giveItems(2572,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(73,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Shamshir*Katana
    if event == "52":
         if st.getQuestItemsCount(73) >= 1 and st.getQuestItemsCount(74) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(73,1)
            if st.getQuestItemsCount(74) >= 1:
               st.takeItems(74,1)
               st.takeItems(1458,954)
               st.giveItems(2573,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(73,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Shamshir*Spirits Sword
    if event == "53":
         if st.getQuestItemsCount(73) >= 1 and st.getQuestItemsCount(131) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(73,1)
            if st.getQuestItemsCount(131) >= 1:
               st.takeItems(131,1)
               st.takeItems(1458,954)
               st.giveItems(2574,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(73,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Shamshir*Raid Sword
    if event == "54":
         if st.getQuestItemsCount(73) >= 1 and st.getQuestItemsCount(133) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(73,1)
            if st.getQuestItemsCount(133) >= 1:
               st.takeItems(133,1)
               st.takeItems(1458,954)
               st.giveItems(2575,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(73,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Katana*Katana
    if event == "55":
         if st.getQuestItemsCount(74) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(74,1)
            if st.getQuestItemsCount(74) >= 1:
               st.takeItems(74,1)
               st.takeItems(1458,954)
               st.giveItems(2582,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(74,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Katana*Spirits Sword
    if event == "56":
         if st.getQuestItemsCount(74) >= 1 and st.getQuestItemsCount(131) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(74,1)
            if st.getQuestItemsCount(131) >= 1:
               st.takeItems(131,1)
               st.takeItems(1458,954)
               st.giveItems(2583,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(74,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spirits Sword*Spirits Sword
    if event == "57":
         if st.getQuestItemsCount(131) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(131,1)
            if st.getQuestItemsCount(131) >= 1:
               st.takeItems(131,1)
               st.takeItems(1458,954)
               st.giveItems(2591,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(131,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Spirits Sword*Raid Sword
    if event == "58":
         if st.getQuestItemsCount(131) >= 1 and st.getQuestItemsCount(133) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(131,1)
            if st.getQuestItemsCount(133) >= 1:
               st.takeItems(133,1)
               st.takeItems(1458,954)
               st.giveItems(2592,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(131,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

# Raid Sword*Raid Sword
    if event == "59":
         if st.getQuestItemsCount(133) >= 1 and st.getQuestItemsCount(1458) >= 954:
            st.takeItems(133,1)
            if st.getQuestItemsCount(133) >= 1:
               st.takeItems(133,1)
               st.takeItems(1458,954)
               st.giveItems(2599,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(133,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

    if event == "0":
      htmltext = "Trade has been canceled."
    
    if htmltext != event:
      st.setState(COMPLETED)
      st.exitQuest(1)

    return htmltext

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   st.set("cond","0")
   st.setState(STARTED)
   return "1.htm"

QUEST       = Quest(1001,"1001_dual_swords","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)


for npcId in SMITHS :
    QUEST.addStartNpc(npcId)
    STARTED.addTalkId(npcId)

print "importing blacksmith data: 1001_dual swords"