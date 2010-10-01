#Created by Bloodshed
import sys
from java.lang	import System
from java.util	import Calendar
from com.l2jserver.gameserver.ai					import CtrlIntention
from com.l2jserver.gameserver.model					import L2CharPosition
from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest
from com.l2jserver.gameserver.network.serverpackets	import NpcSay
from com.l2jserver.gameserver.network.serverpackets	import ExShowScreenMessage

qn = "450_GraveRobberMemberRescue"

KANEMIKA	= 32650
WARRIOR_NPC	= 32651

WARRIOR_MON	= 22741

EVIDENCE_OF_MIGRATION	= 14876
ADENA	= 57

RESET_HOUR = 6
RESET_MIN  = 30

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [EVIDENCE_OF_MIGRATION]

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "32650-05.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = Quest.getNoQuestMsg(player) 
		st = player.getQuestState(qn) 
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		id = st.getState()
		if npcId == KANEMIKA :
			if cond == 0 :
				reset = st.get("reset")
				remain = 0
				if reset and reset.isdigit() :
					remain = long(reset) - System.currentTimeMillis()
				if remain <= 0 :
					if player.getLevel() >= 80 :
						htmltext = "32650-01.htm"
					else :
						htmltext = "32650-00.htm"
						st.exitQuest(True)
				else :
					htmltext = "32650-09.htm"
			elif cond == 1 :
				if st.getQuestItemsCount(EVIDENCE_OF_MIGRATION) >= 1 :
					htmltext = "32650-07.htm"
				else :
					htmltext = "32650-06.htm"
			elif cond == 2 and st.getQuestItemsCount(EVIDENCE_OF_MIGRATION) == 10 :
				htmltext = "32650-08.htm"
				st.giveItems(ADENA,65000)
				st.takeItems(EVIDENCE_OF_MIGRATION,10)
				st.setState(State.COMPLETED)
				st.unset("cond")
				st.exitQuest(False)
				st.playSound("ItemSound.quest_finish")
				reset = Calendar.getInstance()
				reset.set(Calendar.MINUTE, RESET_MIN)
				# if time is >= RESET_HOUR - roll to the next day
				if reset.get(Calendar.HOUR_OF_DAY) >= RESET_HOUR :
					reset.add(Calendar.DATE, 1)
				reset.set(Calendar.HOUR_OF_DAY, RESET_HOUR)
				st.set("reset",str(reset.getTimeInMillis()))
		elif cond == 1 and npcId == WARRIOR_NPC :
			if st.getRandom(100) < 50 :
				htmltext = "32651-01.htm"
				st.giveItems(EVIDENCE_OF_MIGRATION,1)
				st.playSound("ItemSound.quest_itemget")
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(npc.getX()+100, npc.getY()+100, npc.getZ(), 0))
				npc.getSpawn().decreaseCount(npc)
				npc.deleteMe()
				if st.getQuestItemsCount(EVIDENCE_OF_MIGRATION) == 10 :
					st.set("cond","2")
					st.playSound("ItemSound.quest_middle")
			else :
				htmltext = ""
				player.sendPacket(ExShowScreenMessage(1,0,5,0,1,0,0,2,4000,1,"The grave robber warrior has been filled with dark energy and is attacking you!"))
				warrior = st.addSpawn(WARRIOR_MON,npc.getX(),npc.getY(),npc.getZ(),npc.getHeading(),True,600000)
				warrior.setRunning()
				warrior.addDamageHate(player,0,999)
				warrior.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player)
				if st.getRandom(100) < 50 :
					npc.broadcastPacket(NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "...Grunt... oh..."))
					npc.getSpawn().decreaseCount(npc)
					npc.deleteMe()
				else :
					npc.broadcastPacket(NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "Grunt... What's... wrong with me..."))
					npc.getSpawn().decreaseCount(npc)
					npc.deleteMe()
		return htmltext

QUEST		= Quest(450,qn,"Grave Robber Member Rescue")

QUEST.addStartNpc(KANEMIKA)

QUEST.addTalkId(KANEMIKA)
QUEST.addTalkId(WARRIOR_NPC)