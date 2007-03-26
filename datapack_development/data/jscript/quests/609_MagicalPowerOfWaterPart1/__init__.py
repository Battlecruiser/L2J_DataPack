#Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "609_MagicalPowerOfWaterPart1"

#NPC
Wahkan = 31371
Asefa = 31372
Udan_Box = 31561

#MOBS
Varka_Mobs = [ 21350, 21351, 21353, 21354, 21355, 21357, 21358, 21360, 21361, \
21362, 21369, 21370, 21364, 21365, 21366, 21368, 21371, 21372, 21373, 21374, 21375 ]
Ketra_Orcs = [ 21324, 21325, 21327, 21328, 21329, 21331, 21332, 21334, 21335, \
21336, 21338, 21339, 21340, 21342, 21343, 21344, 21345, 21346, 21347, 21348, 21349 ]

#ITEMS
Key = 1661
Totem = 7237
Wisdom_Stone = 7081
Totem2 = 7238

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self, event, st) :
   cond = st.getInt("cond")
   id = st.getInt("id")
   aggro = st.getInt("aggro")
   Thief_Key = st.getQuestItemsCount(Key)
   htmltext = event
   if event == "31371-04.htm" :
       if st.getPlayer().getLevel() >= 74 and st.getPlayer().getAllianceWithVarkaKetra() >= 2 :
            st.set("cond","1")
            st.set("id","2")
            st.set("aggro","0")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
            htmltext = "31371-04.htm"
       else :
            htmltext = "31371-02.htm"
            st.exitQuest(1)
   elif event == "31561-03.htm" :
       if Thief_Key:
           st.takeItems(Key,1)
           if aggro == 1 :
               htmltext = "31561-04.htm"
           else :
               htmletext = "31561-03.htm"
               st.giveItems(Totem,1)
               st.set("id","5")
               st.set("cond","3")
               st.playSound("ItemSound.quest_middle")
       else :
           htmltext = "31561-02.htm"
   return htmltext

 def onTalk (self, npc, player):
    st = player.getQuestState(qn)
    htmltext = "<html><head><body>I have nothing to say you</body></html>"
    if st :
      npcId = npc.getNpcId()
      cond = st.getInt("cond")
      id = st.getInt("id")
      aggro = st.getInt("aggro")
      Green_Totem = st.getQuestItemsCount(Totem)
      Stone = st.getQuestItemsCount(Wisdom_Stone)
      if st.getInt("onlyone") != 1 :
        if st.getState() == CREATED :
            if npcId == Wahkan :
                if Stone :
                    htmltext = "<html><head><body>You already have the stone!</body></html>"
                else :
                    htmltext = "31371-01.htm"
        elif st.getState() == STARTED :
          if npcId == Wahkan :
                  if Stone :
                      htmltext = "<html><head><body>You already have the stone!</body></html>"
                  elif id == 2 :
                        htmltext = "31371-05.htm"
          elif npcId == Asefa :
                if st.getPlayer().getAllianceWithVarkaKetra() >= 2 :
                    if id == 2 :
                        htmltext = "31372-01.htm"
                        st.set("cond","2")
                        st.set("id","3")
                    elif id == 3 :
                        htmltext = "31372-02.htm"
                    elif id == 4 or aggro == 1 :
                        htmltext = "31372-03.htm"
                        st.set("id","3")
                        st.set("aggro","0")
                    elif id == 5 and Green_Totem :
                        htmltext = "31372-04.htm"
                        st.giveItems(Wisdom_Stone,1)
                        st.giveItems(Totem2,1)
                        st.takeItems(Totem,1)
                        st.unset("id")
                        st.unset("aggro")
                        st.playSound("ItemSound.quest_middle")
                        st.setState(COMPLETED)
                        st.set("onlyone","1")
          elif npcId == Udan_Box :
                if st.getPlayer().getAllianceWithVarkaKetra() >= 2 :
                    if id == 3 :
                        htmltext = "31561-01.htm"
    return htmltext

 def onAttack (self, npc, player):
    st = player.getQuestState(qn)
    if st :
        if st.getState() == STARTED :
            npcId = npc.getNpcId()
            cond = st.getInt("cond")
            id = st.getInt("id")
            aggro = st.getInt("aggro")
            Red_Totem = st.getQuestItemsCount(Totem)
            if npcId in Varka_Mobs :
                if id > 2 :
                    st.set("aggro","1")
                    st.set("cond","1")
                    st.set("id","4")
                    if Red_Totem :
                        st.takeItems(Totem,-1)
    return

 def onKill (self, npc, player):
    st = player.getQuestState(qn)
    if st :
        if st.getState() == STARTED :
            npcId = npc.getNpcId()
            cond = st.getInt("cond")
            id = st.getInt("id")
            Red_Totem = st.getQuestItemsCount(Totem)
            if npcId in Ketra_Orcs :
                st.unset("id")
                st.unset("aggro")
                st.exitQuest(1)
                if Red_Totem:
                    st.takeItems(Totem,-1)
    return


QUEST       = Quest(609,qn,"Magical Power of Water - Part 1")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(Wahkan)
QUEST.addTalkId(Wahkan)
QUEST.addTalkId(Asefa)
QUEST.addTalkId(Udan_Box)

STARTED.addQuestDrop(Udan_Box,Totem,1)
for mobId in Varka_Mobs:
    QUEST.addAttackId(mobId)
for mobId in Ketra_Orcs:
    QUEST.addKillId(mobId)

print "importing quests: 609: Magical Power of Water - Part 1"
