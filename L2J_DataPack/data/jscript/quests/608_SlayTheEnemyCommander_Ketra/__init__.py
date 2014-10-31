#Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "608_SlayTheEnemyCommander_Ketra"

#NPC
Kadun = 31370
Mos = 25312

#Quest Items
Mos_Head = 7236
Wisdom_Totem = 7220
Ketra_Alliance_Four = 7214

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "31370-04.htm" :
       if st.getPlayer().getAllianceWithVarkaKetra() == 4 and st.getQuestItemsCount(Ketra_Alliance_Four) :
            if st.getPlayer().getLevel() >= 75 :
                    st.set("cond","1")
                    st.setState(STARTED)
                    st.playSound("ItemSound.quest_accept")
                    htmltext = "31370-04.htm"
            else :
                htmltext = "31370-03.htm"
                st.exitQuest(1)
       else :
            htmltext = "31370-02.htm"
            st.exitQuest(1)
   elif event == "31370-07.htm" :
       st.takeItems(Mos_Head,-1)
       st.giveItems(Wisdom_Totem,1)
       st.addExpAndSp(10000,0)
       st.playSound("ItemSound.quest_finish")
       htmltext = "31370-07.htm"
       st.exitQuest(1)
   return htmltext

 def onTalk (self,npc,player):
    htmltext = "<html><head><body>I have nothing to say you</body></html>"
    st = player.getQuestState(qn)
    if st :
      npcId = npc.getNpcId()
      cond = st.getInt("cond")
      Head = st.getQuestItemsCount(Mos_Head)
      Wisdom = st.getQuestItemsCount(Wisdom_Totem)
      if npcId == Kadun :
          if Wisdom == 0 :
              if Head == 0:
                  if cond != 1 :
                      htmltext = "31370-01.htm"
                  else:
                      htmltext = "31370-06.htm"
              else :
                  htmltext = "31370-05.htm"
          #else:
              #htmltext="<html><head><body>This quest has already been completed</body></html>"
    return htmltext

 def onKill (self,npc,player):
    st = player.getQuestState(qn)
    if st :
      if st.getState() == STARTED :
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId == Mos :
            if st.getPlayer().isAlliedWithKetra() :
                if cond == 1:
                    if st.getPlayer().getAllianceWithVarkaKetra() == 4 and st.getQuestItemsCount(Ketra_Alliance_Four) :
                        st.giveItems(Mos_Head,1)
                        st.set("cond","2")
    return

QUEST       = Quest(608,qn,"Slay The Enemy Commander!")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(Kadun)
QUEST.addTalkId(Kadun)

STARTED.addQuestDrop(Mos,Mos_Head,1)
QUEST.addKillId(Mos)

print "importing quests: 608: Slay The Enemy Commander! (Ketra)"
