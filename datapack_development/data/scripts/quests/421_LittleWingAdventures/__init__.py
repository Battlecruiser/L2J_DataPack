# Upgrade your Hatchling to Strider version 0.2
# by DrLecter & DraX_

#Quest info
QUEST_NUMBER      = 421
QUEST_NAME        = "LittleWingAdventures"
QUEST_DESCRIPTION = "Little Wing's Big Adventures"
qn = "421_LittleWingAdventures"

#Configuration

#Minimum pet and player levels required to complete the quest (defaults 55 and 45)
MIN_PET_LEVEL = 55
MIN_PLAYER_LEVEL = 45
# Maximum distance allowed between pet and owner; if it's reached while talking to any NPC, quest is aborted
MAX_DISTANCE = 100

#Messages
default = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
event_1 = "<html><body>Sage Cronos:<br>Then go and see <font color=\"LEVEL\">Fairy Mimyu</font>, she will help you</body></html>"
error_1 = "<html><body>You're suppossed to own a hatchling and have it summoned to complete this quest.</body></html>"
error_2 = "<html><body>Hey! What happened with the other hatchling you had? This one is different.</body></html>"
error_3 = "<html><body>Sage Cronos:<br>You need to be level "+str(MIN_PLAYER_LEVEL)+" to complete this quest.</body></html>"
error_4 = "<html><body>Sage Cronos:<br>Your pet need to be level "+str(MIN_PET_LEVEL)+" to complete this quest.</body></html>"
error_5 = "Your pet is not a hatchling. Quest Aborted."
error_6 = "Your pet should be nearby. Quest aborted"
error_7  =<html><body>Fairy Mymyu:<br>Why would anyone carry more than one dragon flute? You aren't collecting hatchlings, are you? I hope you got this hatchling through the proper channels! I do not deal with those who traffic in pets! Pets are not chattel!</body></html>
qston_1 = "<html><body>Sage Cronos:<br>So, you want to turn your hatchling into a more powerful creature?<br><br><a action=\"bypass -h Quest "+str(QUEST_NUMBER)+"_"+QUEST_NAME+" 16\">Yes, please tell me how</a><br></body></html>"
qston_2 = "<html><body>Sage Cronos:<br>I've said you need to talk to <font color=\"LEVEL\">Fairy Mimyu</font>!!!. Am i clear???</body></html>"
qston_3 = "<html><body>Fairy Mimyu:<br>You weren't yet able to find the <font color=\"LEVEL\">Fairy Trees of Wind, Star, Twilight and Abyss</font>? Don't give up! They are all in <font color=\"LEVEL\">Hunter's Valley</font></body></html>"
order_1 = "<html><body>Fairy Mimyu:<br>Your pet must drink the sap of <font color=\"LEVEL\">Fairy Trees of Wind, Star, Twilight and Abyss</font> to grow up. The trees will probably agree but as we don't want to hurt them, take that leafs to heal any wound your hatchling could cause them</body></html>"
end_msg = "<html><body>Fairy Mimyu:<br>Great job, your hatchling"
end_msg2= "has become an strider, enjoy!</body></html>"

#Quest items
FT_LEAF = 4325
CONTROL_ITEMS = { 3500:4422, 3501:4423, 3502:4424 }

#NPCs
SG_CRONOS = 30610
FY_MYMYU  = 30747

#Mobs
GUARDIAN = 27189

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.network.serverpackets import NpcSay

def get_control_item(st) :
  item = st.getPlayer().getPet().getControlItemId()
  if st.getState() == State.CREATED :
      st.set("item",str(item))
  else :
      if  st.getInt("item") != item : item = 0
  return item  

def get_distance(player) :
    is_far = False
    if abs(player.getPet().getX() - player.getX()) > MAX_DISTANCE :
        is_far = True
    if abs(player.getPet().getY() - player.getY()) > MAX_DISTANCE :
        is_far = True
    if abs(player.getPet().getZ() - player.getZ()) > MAX_DISTANCE :
        is_far = True
    return is_far

class Quest (JQuest) :

 def __init__(self,id,name,descr):
   JQuest.__init__(self,id,name,descr)
   self.questItemIds = [FT_LEAF]
   self.killedTrees = []

 def onEvent (self,event,st) :
    htmltext = event
    if event == "16" :
       htmltext = event_1
       st.setState(State.STARTED)
       st.set("id","0")
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
    return htmltext

 def onTalk (self,npc,player):
   htmltext = default
   st = player.getQuestState(qn)
   if not st : return htmltext
   id = st.getState()
   cond = st.getInt("cond")
   if id == State.COMPLETED :
      st.setState(State.CREATED)
      id = State.CREATED
   npcId = npc.getNpcId()
   if player.getPet() == None :
       htmltext = error_1
       st.exitQuest(1)
   elif player.getPet().getTemplate().npcId not in [12311,12312,12313] : #npcIds for hatchlings
       htmltext = error_5
       st.exitQuest(1)
   elif player.getPet().getLevel() < MIN_PET_LEVEL :
       st.exitQuest(1)
       htmltext = error_4
   elif get_distance(player) :
       st.exitQuest(1)
       htmltext = error_6
   elif get_control_item(st) == 0 :
       st.exitQuest(1)
       htmltext = error_2
   elif npcId == SG_CRONOS :
      if id == State.CREATED :
         if player.getLevel() < MIN_PLAYER_LEVEL :
            st.exitQuest(1)
            htmltext = error_3
         else :   
            htmltext = qston_1
      else :
         htmltext = qston_2
   elif npcId == FY_MYMYU :
     if id == State.STARTED and cond < 3 :
        if st.getQuestItemsCount(FT_LEAF) == 0 and st.getInt("id") == 0 :
           st.set("cond","2")
           st.giveItems(FT_LEAF,4)
           st.playSound("ItemSound.quest_itemget")
           htmltext = order_1
        else :
            htmltext = qston_3
     elif id == State.STARTED and cond >= 3:
     
        name = player.getPet().getName()
        if name == None : name = " "
        else : name = " "+name+" "
        htmltext = end_msg+name+end_msg2
        item=CONTROL_ITEMS[player.getInventory().getItemByObjectId(player.getPet().getControlItemId()).getItemId()]
        player.getPet().deleteMe(player) #both despawn pet and delete controlitem
        st.giveItems(item,1)
        st.exitQuest(1)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onAttack(self, npc, player, damage, isPet) :
   st = player.getQuestState(str(QUEST_NUMBER)+"_"+QUEST_NAME)
   if not st:
     return
   npcId = npc.getNpcId()
   for pc, mobId, in self.killedTrees:
      if pc == player and mobId == npcId:
         return
   if isPet :
      pet = player.getPet()
      if st.getRandom(100) <= 2 and st.getQuestItemsCount(FT_LEAF) >= 0:
         st.takeItems(FT_LEAF,1)
         st.playSound("ItemSound.quest_middle")
         npc.broadcastPacket(NpcSay(npc.getNpcId(),0,npcId,"gives me spirit leaf...!"))
         self.killedTrees.append([player,npcId])
         if st.getQuestItemsCount(FT_LEAF) == 0 :
            st.set("cond","3")
   return 

# Quest class and state definition
QUEST       = Quest(QUEST_NUMBER, str(QUEST_NUMBER)+"_"+QUEST_NAME, QUEST_DESCRIPTION)

# Quest NPC starter initialization
QUEST.addStartNpc(SG_CRONOS)
# Quest initialization
QUEST.addTalkId(SG_CRONOS)

QUEST.addTalkId(FY_MYMYU)

for i in range(27185,27189):
   QUEST.addAttackId(i)
