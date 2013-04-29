import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest

qn = "634_InSearchofDimensionalFragments"

DIMENSION_FRAGMENT_ID = 7079

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [DIMENSION_FRAGMENT_ID]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if event == "2a.htm" :
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
      st.set("cond","1")
    elif event == "5.htm" :
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(1)
    return htmltext

 def onTalk (self,npc,player):
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if st :
        npcId = npc.getNpcId()
        id = st.getState()
        if id == State.CREATED :
            if player.getLevel() < 20 :
                st.exitQuest(1)
                htmltext="1.htm"
            else:
                htmltext="2.htm"
        elif id == State.STARTED :
            htmltext = "4.htm"
   return htmltext

 def onKill(self,npc,player,isPet):
    partyMember = self.getRandomPartyMemberState(player, State.STARTED)
    if not partyMember : return
    st = partyMember.getQuestState(qn)
    if st :
        if st.getState() == State.STARTED :
            itemMultiplier,chance = divmod(80*Config.RATE_QUEST_DROP,1000)
            if self.getRandom(1000) < chance :
                itemMultiplier += 1
            numItems = int(itemMultiplier * (npc.getLevel() * 0.15 +1.6))
            if numItems > 0 :    
                st.giveItems(DIMENSION_FRAGMENT_ID,numItems)
    return

QUEST       = Quest(634, qn, "In Search of Dimensional Fragments")

for npcId in range(31494,31508):
  QUEST.addTalkId(npcId)
  QUEST.addStartNpc(npcId)

for mobs in range(21208,21256):
  QUEST.addKillId(mobs)