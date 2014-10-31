print "importing blacksmith data: 1005_unseal"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

# Sealed Dark Crystal Gloves*Dark Crystal Gloves Heavy
    if event == "1":
         if st.getQuestItemsCount(5290) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5290,1)
             st.takeItems(5575,100000)
             st.giveItems(5765,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Gloves*Dark Crystal Gloves Light
    if event == "2":
         if st.getQuestItemsCount(5290) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5290,1)
             st.takeItems(5575,100000)
             st.giveItems(5766,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Gloves*Dark Crystal Gloves Robe
    if event == "3":
         if st.getQuestItemsCount(5290) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5290,1)
             st.takeItems(5575,100000)
             st.giveItems(5767,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Gloves*Tallum Gloves Heavy
    if event == "4":
         if st.getQuestItemsCount(5295) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5295,1)
             st.takeItems(5575,100000)
             st.giveItems(5768,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Gloves*Tallum Gloves Light
    if event == "5":
         if st.getQuestItemsCount(5295) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5295,1)
             st.takeItems(5575,100000)
             st.giveItems(5769,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Gloves*Tallum Gloves Robe
    if event == "6":
         if st.getQuestItemsCount(5295) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5295,1)
             st.takeItems(5575,100000)
             st.giveItems(5770,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Gloves*Nightmare Gloves Heavy
    if event == "7":
         if st.getQuestItemsCount(5313) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5313,1)
             st.takeItems(5575,100000)
             st.giveItems(5771,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Gloves*Nightmare Gloves Light
    if event == "8":
         if st.getQuestItemsCount(5313) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5313,1)
             st.takeItems(5575,100000)
             st.giveItems(5772,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Gloves*Nightmare Gloves Robe
    if event == "9":
         if st.getQuestItemsCount(5313) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5313,1)
             st.takeItems(5575,100000)
             st.giveItems(5773,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Gloves*Majestic Gloves Heavy
    if event == "10":
         if st.getQuestItemsCount(5318) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5318,1)
             st.takeItems(5575,100000)
             st.giveItems(5774,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Gloves*Majestic Gloves Light
    if event == "11":
         if st.getQuestItemsCount(5318) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5318,1)
             st.takeItems(5575,100000)
             st.giveItems(5775,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Gloves*Majestic Gloves Robe
    if event == "12":
         if st.getQuestItemsCount(5318) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5318,1)
             st.takeItems(5575,100000)
             st.giveItems(5776,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Boots*Dark Crystal Boots Heavy
    if event == "13":
         if st.getQuestItemsCount(5291) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5291,1)
             st.takeItems(5575,100000)
             st.giveItems(5777,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Boots*Dark Crystal Boots Light
    if event == "14":
         if st.getQuestItemsCount(5291) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5291,1)
             st.takeItems(5575,100000)
             st.giveItems(5778,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Boots*Dark Crystal Boots Robe
    if event == "15":
         if st.getQuestItemsCount(5291) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5291,1)
             st.takeItems(5575,100000)
             st.giveItems(5779,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Boots*Tallum Boots Heavy
    if event == "16":
         if st.getQuestItemsCount(5296) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5296,1)
             st.takeItems(5575,100000)
             st.giveItems(5780,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Boots*Tallum Boots Light
    if event == "17":
         if st.getQuestItemsCount(5296) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5296,1)
             st.takeItems(5575,100000)
             st.giveItems(5781,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Boots*Tallum Boots Robe
    if event == "18":
         if st.getQuestItemsCount(5296) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5296,1)
             st.takeItems(5575,100000)
             st.giveItems(5782,1)
             shtmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Boots*Nightmare Boots Heavy
    if event == "19":
         if st.getQuestItemsCount(5314) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5314,1)
             st.takeItems(5575,100000)
             st.giveItems(5783,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Boots*Nightmare Boots Light
    if event == "20":
         if st.getQuestItemsCount(5314) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5314,1)
             st.takeItems(5575,100000)
             st.giveItems(5784,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Boots*Nightmare Boots Robe
    if event == "21":
         if st.getQuestItemsCount(5314) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5314,1)
             st.takeItems(5575,100000)
             st.giveItems(5785,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Boots*Majestic Boots Heavy
    if event == "22":
         if st.getQuestItemsCount(5319) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5319,1)
             st.takeItems(5575,100000)
             st.giveItems(5786,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Boots*Majestic Boots Light
    if event == "23":
         if st.getQuestItemsCount(5319) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5319,1)
             st.takeItems(5575,100000)
             st.giveItems(5787,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Boots*Majestic Boots Robe
    if event == "24":
         if st.getQuestItemsCount(5319) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5319,1)
             st.takeItems(5575,100000)
             st.giveItems(5788,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Helm
    if event == "25":
         if st.getQuestItemsCount(5289) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5289,1)
             st.takeItems(5575,100000)
             st.giveItems(512,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Helm
    if event == "26":
         if st.getQuestItemsCount(5294) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5294,1)
             st.takeItems(5575,100000)
             st.giveItems(547,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Helm
    if event == "27":
         if st.getQuestItemsCount(5312) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5312,1)
             st.takeItems(5575,100000)
             st.giveItems(2418,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Helm
    if event == "28":
         if st.getQuestItemsCount(5317) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5317,1)
             st.takeItems(5575,100000)
             st.giveItems(2419,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Breast Plate
    if event == "29":
         if st.getQuestItemsCount(5287) >= 1 and st.getQuestItemsCount(5575) >= 150000:
             st.takeItems(5287,1)
             st.takeItems(5575,150000)
             st.giveItems(365,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Leather Mail
    if event == "30":
         if st.getQuestItemsCount(5297) >= 1 and st.getQuestItemsCount(5575) >= 150000:
             st.takeItems(5297,1)
             st.takeItems(5575,150000)
             st.giveItems(2385,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Robe
    if event == "31":
         if st.getQuestItemsCount(5308) >= 1 and st.getQuestItemsCount(5575) >= 150000:
             st.takeItems(5308,1)
             st.takeItems(5575,150000)
             st.giveItems(2407,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Plate Armor
    if event == "32":
         if st.getQuestItemsCount(5293) >= 1 and st.getQuestItemsCount(5575) >= 150000:
             st.takeItems(5293,1)
             st.takeItems(5575,150000)
             st.giveItems(2382,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Leather Mail
    if event == "33":
         if st.getQuestItemsCount(5301) >= 1 and st.getQuestItemsCount(5575) >= 150000:
             st.takeItems(5301,1)
             st.takeItems(5575,150000)
             st.giveItems(2393,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Robe
    if event == "34":
         if st.getQuestItemsCount(5304) >= 1 and st.getQuestItemsCount(5575) >= 150000:
             st.takeItems(5304,1)
             st.takeItems(5575,150000)
             st.giveItems(2400,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Armor
    if event == "35":
         if st.getQuestItemsCount(5311) >= 1 and st.getQuestItemsCount(5575) >= 350000:
             st.takeItems(5311,1)
             st.takeItems(5575,350000)
             st.giveItems(374,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Leather Armor
    if event == "36":
         if st.getQuestItemsCount(5320) >= 1 and st.getQuestItemsCount(5575) >= 350000:
             st.takeItems(5320,1)
             st.takeItems(5575,350000)
             st.giveItems(2394,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Nightmare Robe
    if event == "37":
         if st.getQuestItemsCount(5326) >= 1 and st.getQuestItemsCount(5575) >= 350000:
             st.takeItems(5326,1)
             st.takeItems(5575,350000)
             st.giveItems(2408,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Plate Armor
    if event == "38":
         if st.getQuestItemsCount(5316) >= 1 and st.getQuestItemsCount(5575) >= 350000:
             st.takeItems(5316,1)
             st.takeItems(5575,350000)
             st.giveItems(2383,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Leather Armor
    if event == "39":
         if st.getQuestItemsCount(5323) >= 1 and st.getQuestItemsCount(5575) >= 350000:
             st.takeItems(5323,1)
             st.takeItems(5575,350000)
             st.giveItems(2395,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Robe
    if event == "40":
         if st.getQuestItemsCount(5329) >= 1 and st.getQuestItemsCount(5575) >= 350000:
             st.takeItems(5329,1)
             st.takeItems(5575,350000)
             st.giveItems(2409,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Gaiters
    if event == "41":
         if st.getQuestItemsCount(5288) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5288,1)
             st.takeItems(5575,100000)
             st.giveItems(388,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Leggings
    if event == "42":
         if st.getQuestItemsCount(5298) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5298,1)
             st.takeItems(5575,100000)
             st.giveItems(2389,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Tallum Stockings
    if event == "43":
         if st.getQuestItemsCount(5305) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5305,1)
             st.takeItems(5575,100000)
             st.giveItems(2405,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Phoenix's Necklace
    if event == "44":
         if st.getQuestItemsCount(6323) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(6323,1)
             st.takeItems(5575,100000)
             st.giveItems(933,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Phoenix's Earing
    if event == "45":
         if st.getQuestItemsCount(6324) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(6324,1)
             st.takeItems(5575,100000)
             st.giveItems(871,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Phoenix's Ring
    if event == "46":
         if st.getQuestItemsCount(6325) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(6325,1)
             st.takeItems(5575,100000)
             st.giveItems(902,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Necklace
    if event == "47":
         if st.getQuestItemsCount(6326) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(6326,1)
             st.takeItems(5575,100000)
             st.giveItems(924,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Earing
    if event == "48":
         if st.getQuestItemsCount(6327) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(6327,1)
             st.takeItems(5575,100000)
             st.giveItems(862,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Majestic Ring
    if event == "49":
         if st.getQuestItemsCount(6328) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(6328,1)
             st.takeItems(5575,100000)
             st.giveItems(893,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Dark Crystal Shield
    if event == "50":
         if st.getQuestItemsCount(5292) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5292,1)
             st.takeItems(5575,100000)
             st.giveItems(641,1)
             htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Sealed Shield of Nightmare
    if event == "51":
         if st.getQuestItemsCount(5315) >= 1 and st.getQuestItemsCount(5575) >= 100000:
             st.takeItems(5315,1)
             st.takeItems(5575,100000)
             st.giveItems(2498,1)
             htmltext = "Item has been succesfully unsealed."
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

QUEST       = Quest(1005,"1005_unseal","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(8126)

STARTED.addTalkId(8126)




