# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPC
BABENCO = 7334
BATHIS  = 7332

#MOBS
M_LIZARDMAN       = 919
M_LIZARDMAN_SCOUT = 920
M_LIZARDMAN_GUARD = 921
GIANT_ARANEID     = 925

#QUEST DROPS
BLACK_BONE_NECKLACE = 7178
RED_BONE_NECKLACE   = 7179
INCENSE_POUCH       = 7180
GEM_OF_MAILLE       = 7181

#REWARDS
GREEN_COLORED_LURE_HG = 6521
BABY_DUCK_RODE        = 6529
FISHING_SHOT_NG       = 6535

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7334-1.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7332-1.htm" :
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7332-3.htm" :
     if st.getQuestItemsCount(BLACK_BONE_NECKLACE) == st.getQuestItemsCount(RED_BONE_NECKLACE) == 100 :
       st.takeItems(BLACK_BONE_NECKLACE,100)
       st.takeItems(RED_BONE_NECKLACE,100)
       st.set("cond","4")
       st.set("id","4")
       st.playSound("ItemSound.quest_middle")
     else :
       htmltext = "You don't have required items"
   elif event == "7332-5.htm" :
     if st.getQuestItemsCount(INCENSE_POUCH) == st.getQuestItemsCount(GEM_OF_MAILLE) == 30 :
       st.takeItems(INCENSE_POUCH,30)
       st.takeItems(GEM_OF_MAILLE,30)
       st.giveItems(GREEN_COLORED_LURE_HG,60)
       st.giveItems(BABY_DUCK_RODE,1)
       st.giveItems(FISHING_SHOT_NG,500)
       st.unset("cond")
       st.setState(COMPLETED)
       st.playSound("ItemSound.quest_finish")
     else :
       htmltext = "You don't have required items"
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = int(st.get("cond"))

   if id == CREATED :
     if st.getPlayer().getLevel() >= 20 :
       htmltext = "7334-0.htm"
     else :
       htmltext = htmlhead + "Quest for characters level 20 and above." + htmlfoot
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == BATHIS :
     if cond == 1 :
       htmltext = "7332-0.htm"
     elif st.getQuestItemsCount(BLACK_BONE_NECKLACE) == st.getQuestItemsCount(RED_BONE_NECKLACE) == 100 :
       htmltext = "7332-2.htm"
     elif st.getQuestItemsCount(INCENSE_POUCH) == st.getQuestItemsCount(GEM_OF_MAILLE) == 30 :
       htmltext = "7332-4.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   cond = int(st.get("cond"))

   if npcId in [919,920] and cond == 2 and st.getQuestItemsCount(BLACK_BONE_NECKLACE) < 100 :
     st.giveItems(BLACK_BONE_NECKLACE,1)
     if st.getQuestItemsCount(BLACK_BONE_NECKLACE) == 100 and st.getQuestItemsCount(RED_BONE_NECKLACE) == 100:
       st.set("cond","3")
       st.set("id","3")
       st.playSound("ItemSound.quest_middle")
     else:
       st.playSound("ItemSound.quest_itemget")
   if npcId == M_LIZARDMAN_GUARD and cond == 2 and st.getQuestItemsCount(RED_BONE_NECKLACE) < 100 :
     st.giveItems(RED_BONE_NECKLACE,1)
     if st.getQuestItemsCount(BLACK_BONE_NECKLACE) == 100 and st.getQuestItemsCount(RED_BONE_NECKLACE) == 100:
       st.set("cond","3")
       st.set("id","3")
       st.playSound("ItemSound.quest_middle")
     else:
       st.playSound("ItemSound.quest_itemget")
   if npcId in [920,921] and cond == 4 and st.getQuestItemsCount(INCENSE_POUCH) < 30 :
     st.giveItems(INCENSE_POUCH,1)
     if st.getQuestItemsCount(INCENSE_POUCH) == 30 and st.getQuestItemsCount(GEM_OF_MAILLE) == 30:
       st.set("cond","5")
       st.set("id","5")
       st.playSound("ItemSound.quest_middle")
     else:
       st.playSound("ItemSound.quest_itemget")
   if npcId == GIANT_ARANEID and cond == 4 and st.getQuestItemsCount(GEM_OF_MAILLE) < 30 :
     st.giveItems(GEM_OF_MAILLE,1)
     if st.getQuestItemsCount(INCENSE_POUCH) == 30 and st.getQuestItemsCount(GEM_OF_MAILLE) == 30:
       st.set("cond","5")
       st.set("id","5")
       st.playSound("ItemSound.quest_middle")
     else:
       st.playSound("ItemSound.quest_itemget")
   return

qnum  = 39
qdef  = str(qnum) + "_RedEyedInvaders"
qname = "Red-Eyed Invaders"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(BABENCO)

CREATED.addTalkId(BABENCO)

STARTED.addTalkId(BATHIS)

COMPLETED.addTalkId(BABENCO)

STARTED.addKillId(M_LIZARDMAN)
STARTED.addKillId(M_LIZARDMAN_SCOUT)
STARTED.addKillId(M_LIZARDMAN_GUARD)
STARTED.addKillId(GIANT_ARANEID)

STARTED.addQuestDrop(BABENCO,BLACK_BONE_NECKLACE,1)
STARTED.addQuestDrop(BABENCO,RED_BONE_NECKLACE,1)
STARTED.addQuestDrop(BABENCO,INCENSE_POUCH,1)
STARTED.addQuestDrop(BABENCO,GEM_OF_MAILLE,1)

print "importing quests: " + str(qnum) + ": " + qname