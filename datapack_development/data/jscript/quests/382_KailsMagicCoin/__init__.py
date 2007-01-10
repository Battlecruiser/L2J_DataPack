# Kail's Magic Coin ver. 0.1 by DrLecter
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

QuestNumber      = 382
QuestName        = "KailsMagicCoin"
QuestDescription = "Kail's Magic Coin"

#Messages
default = "<html><head><body>I have nothing to say to you.</body></html>"
#Quest items
ROYAL_MEMBERSHIP = 5898
#NPCs
VERGARA = 30687
#MOBs and CHANCES
MOBS={21017:[5961],21019:[5962],21020:[5963],21022:[5961,5962,5963]}
CHANCE = 10*Config.RATE_DROP_QUEST

class Quest (JQuest) :

  def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

  def onEvent (self,event,st) :
      htmltext = event
      if event == "30687-03.htm":
         if st.getPlayer().getLevel() >= 55 and st.getQuestItemsCount(ROYAL_MEMBERSHIP) :
            st.set("cond","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
         else :
            htmltext = "30687-01.htm"
            st.exitQuest(1)
      return htmltext

  def onTalk(self,npc,st):
      cond=st.getInt("cond")
      npcId = npc.getNpcId()
      htmltext = default
      if st.getQuestItemsCount(ROYAL_MEMBERSHIP) == 0 or st.getPlayer().getLevel() < 55 :
         htmltext = "30687-01.htm"
         st.exitQuest(1)
      else :
         if cond == 0 :
            htmltext = "30687-02.htm"
         else :
            htmltext = "30687-04.htm"
      return htmltext

  def onKill (self,npc,st):
      if st.getRandom(100) < CHANCE and st.getQuestItemsCount(ROYAL_MEMBERSHIP) :
         npcId = npc.getNpcId()
         st.giveItems(MOBS[npcId][st.getRandom(len(MOBS[npcId]))],1)
         st.playSound("ItemSound.quest_itemget")
      return

QUEST       = Quest(QuestNumber, str(QuestNumber)+"_"+QuestName, QuestDescription)
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(VERGARA)

CREATED.addTalkId(VERGARA)
STARTED.addTalkId(VERGARA)

for npc in MOBS.keys():
    STARTED.addKillId(npc)

for coin in range(5961,5964):
    STARTED.addQuestDrop(coin,VERGARA,1)

print "importing quests: "+str(QuestNumber)+": "+QuestDescription
