import sys
from com.l2jserver.gameserver.model.quest        			import State
from com.l2jserver.gameserver.model.quest        			import QuestState
from com.l2jserver.gameserver.model.quest.jython 			import QuestJython as JQuest
from com.l2jserver.gameserver.network.serverpackets      	import PledgeShowInfoUpdate
from com.l2jserver.gameserver.network.serverpackets      	import SystemMessage
from com.l2jserver.util import Rnd

qn="509_TheClansPrestige"

# Quest NPC
VALDIS = 31331

# Quest Items
DAIMONS_EYES = 8489 # Daimon's Eyes: Eyes obtained by killing Daimon the White-Eyed.
HESTIAS_FAIRY_STONE = 8490 # Hestia's Fairy Stone: Fairy Stone obtained by defeating Hestia, the Guardian Deity of the Hot Springs.
NUCLEUS_OF_LESSER_GOLEM = 8491 # Nucleus of Lesser Golem: Nucleus obtained by defeating the Lesser Golem.
FALSTON_FANG = 8492 # Falston's Fang: Fangs obtained by killing Falston, the Demon's Agent.

#Quest Raid Bosses
DAIMON_THE_WHITE_EYED = 25290
HESTIA_GUARDIAN_DEITY = 25293
PLAGUE_GOLEM = 25523
DEMONS_AGENT_FALSTON = 25322

# id:[RaidBossNpcId,questItemId,ClanPoints]
REWARDS_LIST={
    1:[DAIMON_THE_WHITE_EYED,	DAIMONS_EYES,1378],
    2:[HESTIA_GUARDIAN_DEITY, HESTIAS_FAIRY_STONE,1378],
    3:[PLAGUE_GOLEM, NUCLEUS_OF_LESSER_GOLEM,1070],
    4:[DEMONS_AGENT_FALSTON, FALSTON_FANG,782],
    }

RADAR={
    1:[186320,-43904,-3175],
    2:[134672,-115600,-1216],
    3:[170000,-60000,-3500],
    4:[93296,-75104,-1824],
    }

class Quest (JQuest) :

 def __init__(self,id,name,descr) :
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [DAIMONS_EYES,HESTIAS_FAIRY_STONE,NUCLEUS_OF_LESSER_GOLEM,FALSTON_FANG]

 def onAdvEvent (self,event,npc,player) :
  st = player.getQuestState(qn)
  if not st: return
  cond = st.getInt("cond")
  htmltext=event
  if event == "31331-0.htm" :
    if cond == 0 :
      st.set("cond","1")
      st.setState(State.STARTED)
  elif event.isdigit() :
    if int(event) in REWARDS_LIST.keys():
      st.set("raid",event)
      htmltext="31331-"+event+".htm"
      x,y,z=RADAR[int(event)]
      if x+y+z:
        st.addRadar(x, y, z)
      st.playSound("ItemSound.quest_accept")
  elif event == "31331-5.htm" :
    st.playSound("ItemSound.quest_finish")
    st.exitQuest(1)
  return htmltext

 def onTalk (self,npc,player) :
  htmltext = Quest.getNoQuestMsg(player)
  st = player.getQuestState(qn)
  if not st : return htmltext
  clan = player.getClan()
  npcId = npc.getNpcId()
  if player.getClan() == None or player.isClanLeader() == 0 :
     st.exitQuest(1)
     htmltext = "31331-0a.htm"
  elif player.getClan().getLevel() < 6 :
     st.exitQuest(1)
     htmltext =  "31331-0b.htm"
  else :
     cond = st.getInt("cond")
     raid = st.getInt("raid")
     id = st.getState()
     if id == State.CREATED and cond == 0 :
        htmltext =  "31331-0c.htm"
     elif id == State.STARTED and cond == 1 and raid in REWARDS_LIST.keys() :
        npc,item,point=REWARDS_LIST[raid]
        count = st.getQuestItemsCount(item)
        if not count :
           htmltext = "31331-"+str(raid)+"a.htm"
        elif count == 1 :
           htmltext = "31331-"+str(raid)+"b.htm"
           st.takeItems(item,1)
           clan.addReputationScore(point,True)
           player.sendPacket(SystemMessage.getSystemMessage(1777).addNumber(point))
           clan.broadcastToOnlineMembers(PledgeShowInfoUpdate(clan))
  return htmltext

 def onKill(self,npc,player,isPet) :
  st = 0
  if player.isClanLeader() :
   st = player.getQuestState(qn)
  else:
   clan = player.getClan()
   if clan:
    leader=clan.getLeader()
    if leader :
     pleader= leader.getPlayerInstance()
     if pleader :
      if player.isInsideRadius(pleader, 1500, 1, 0) :
       st = pleader.getQuestState(qn)
  if not st : return
  option=st.getInt("raid")
  if st.getInt("cond") == 1 and st.getState() == State.STARTED and option in REWARDS_LIST.keys():
   raid,item,point = REWARDS_LIST[option]
   npcId=npc.getNpcId()
   if npcId == raid and not st.getQuestItemsCount(item) :
      st.giveItems(item,1)
      st.playSound("ItemSound.quest_middle")
  return


# Quest class and state definition
QUEST       = Quest(509,qn,"The Clan's Prestige")

QUEST.addStartNpc(VALDIS)
QUEST.addTalkId(VALDIS)

for npc,item,point in REWARDS_LIST.values():
    QUEST.addKillId(npc)