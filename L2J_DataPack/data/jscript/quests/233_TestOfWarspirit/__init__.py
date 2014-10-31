# Maked by Mr. Have fun! Version 0.2
# rewritten by Rolarga, Version 0.3

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_WARSPIRIT_ID = 2879
VENDETTA_TOTEM_ID = 2880
TAMLIN_ORC_HEAD_ID = 2881
WARSPIRIT_TOTEM_ID = 2882
ORIMS_CONTRACT_ID = 2883
PORTAS_EYE_ID = 2884
BRAKIS_REMAINS1_ID = 2887
HERMODTS_REMAINS1_ID = 2901
KIRUNAS_REMAINS1_ID = 2910
TONARS_REMAINS1_ID = 2894
BRAKIS_REMAINS2_ID = 2911
HERMODTS_REMAINS2_ID = 2913
KIRUNAS_REMAINS2_ID = 2914
TONARS_REMAINS2_ID = 2912
EXCUROS_SCALE_ID = 2885
MORDEOS_TALON_ID = 2886
PEKIRONS_TOTEM_ID = 2888
TONARS_SKULL_ID = 2889
TONARS_RIB_BONE_ID = 2890
TONARS_SPINE_ID = 2891
TONARS_ARM_BONE_ID = 2892
TONARS_THIGH_BONE_ID = 2893
MANAKIAS_TOTEM_ID = 2895
HERMODTS_SKULL_ID = 2896
HERMODTS_RIB_BONE_ID = 2897
HERMODTS_SPINE_ID = 2898
HERMODTS_ARM_BONE_ID = 2899
HERMODTS_THIGH_BONE_ID = 2900
RACOYS_TOTEM_ID = 2902
KIRUNAS_SKULL_ID = 2905
KIRUNAS_RIB_BONE_ID = 2906
KIRUNAS_SPINE_ID = 2907
KIRUNAS_ARM_BONE_ID = 2908
KIRUNAS_THIGH_BONE_ID = 2909
INSECT_DIAGRAM_BOOK_ID = 2904
VIVIANTES_LETTER_ID = 2903

NPC=[7030,7436,7507,7510,7515,7630,7649,7682]

STATS=["cond","step","Orim","Racoy","Perkiron","Manakia","Manakia_Queen"]


#npcId=[[accepted values for this part],variable for the current part from the mob,maxcount,chance in %, items to give(one per kill max)]
DROPLIST={
213: [[2,3,4],"Orim",10,100,[PORTAS_EYE_ID]],
214: [[2,3,4],"Orim",10,100,[EXCUROS_SCALE_ID]],
215: [[2,3,4],"Orim",10,100,[MORDEOS_TALON_ID]],
601: [[1],"step",13,50,[TAMLIN_ORC_HEAD_ID]],
602: [[1],"step",13,50,[TAMLIN_ORC_HEAD_ID]],
5108:[[2],"Manakia_Queen",1,100,[HERMODTS_SKULL_ID]],
581: [[2,3,4,5,6],"Perkiron",1,50,[TONARS_RIB_BONE_ID,TONARS_SPINE_ID,TONARS_ARM_BONE_ID,TONARS_SKULL_ID,TONARS_THIGH_BONE_ID]],
582: [[2,3,4,5,6],"Perkiron",1,50,[TONARS_SKULL_ID,TONARS_ARM_BONE_ID,TONARS_RIB_BONE_ID,TONARS_SPINE_ID,TONARS_THIGH_BONE_ID]],
158: [[2,3,4,5],"Manakia",1,50,[HERMODTS_RIB_BONE_ID,HERMODTS_SPINE_ID,HERMODTS_ARM_BONE_ID,HERMODTS_THIGH_BONE_ID]],
89:  [[4,5,6,7,8,9],"Racoy",1,100,[[KIRUNAS_THIGH_BONE_ID,KIRUNAS_ARM_BONE_ID],[KIRUNAS_SPINE_ID,KIRUNAS_RIB_BONE_ID],[KIRUNAS_SKULL_ID]]],
90:  [[4,5,6,7,8,9],"Racoy",1,100,[[KIRUNAS_THIGH_BONE_ID,KIRUNAS_ARM_BONE_ID],[KIRUNAS_SPINE_ID,KIRUNAS_RIB_BONE_ID],[KIRUNAS_SKULL_ID]]]
}

# Mob List initialisation for the different Parts
PART2_MOBS = [601,602]
PART1_MOBS = []

for mob in DROPLIST.keys():
	if mob in PART2_MOBS:
		continue
	PART1_MOBS.append(mob)


class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)
	
	def onEvent (self,event,st) :
		htmltext = event
		if event == "1" :
			htmltext = "7510-05.htm"
			for var in STATS:
				st.set(var,"1")
			st.setState(PART1)
			st.playSound("ItemSound.quest_accept")
		elif event == "7630_1" :
			htmltext = "7630-02.htm"
		elif event == "7630_2" :
			htmltext = "7630-03.htm"
		elif event == "7630_3" :
			htmltext = "7630-04.htm"
			st.giveItems(ORIMS_CONTRACT_ID,1)
			st.set("Orim","2")
		elif event == "7682_1" :
			htmltext = "7682-02.htm"
			st.giveItems(PEKIRONS_TOTEM_ID,1)
			st.set("Perkiron","2")
		elif event == "7515_1" :
			htmltext = "7515-02.htm"
			st.giveItems(MANAKIAS_TOTEM_ID,1)
			st.set("Manakia","2")
			st.set("Manakia_Queen","2")
		elif event == "7507_1" :
			htmltext = "7507-02.htm"
			st.giveItems(RACOYS_TOTEM_ID,1)
			st.set("Racoy","2")
		elif event == "7030_1" :
			htmltext = "7030-02.htm"
		elif event == "7030_2" :
			htmltext = "7030-03.htm"
		elif event == "7030_3" :
			htmltext = "7030-04.htm"
			st.giveItems(VIVIANTES_LETTER_ID,1)
			st.set("Racoy","3")
		elif event == "7649_1" :
			htmltext = "7649-02.htm"
		elif event == "7649_2" :
			st.takeItems(WARSPIRIT_TOTEM_ID,-1)
			st.takeItems(BRAKIS_REMAINS2_ID,-1)
			st.takeItems(HERMODTS_REMAINS2_ID,-1)
			st.takeItems(KIRUNAS_REMAINS2_ID,-1)
			st.addExpAndSp(63483,17500)
			st.takeItems(TONARS_REMAINS2_ID,-1)
			st.giveItems(MARK_OF_WARSPIRIT_ID,1)
			htmltext = "7649-03.htm"
			for var in STATS:
				st.unset(var)
			st.setState(COMPLETED)
			st.playSound("ItemSound.quest_finish")
		return htmltext



	def onTalk(self,npc,st):
		npcId=npc.getNpcId()
		id =  st.getState()
		htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
		# first time when a player join the quest
		if id == CREATED:
			for var in STATS:
				st.set(var,"0")
			if st.getPlayer().getClassId().getId() == 0x32:
				if st.getPlayer().getLevel() > 38:
					htmltext = "7510-04.htm"
				else :
					htmltext = "7510-03.htm"
					st.exitQuest(1)
			elif st.getPlayer().getRace().ordinal() == 3:
				htmltext = "7510-02.htm"
				st.exitQuest(1)
			else:
				htmltext = "7510-01.htm"
				st.exitQuest(1)
			return htmltext
		# if quest is already completed
		elif id == COMPLETED:
			return "<html><head><body>This quest has already been completed.</body></html>"
		# if quest is accepted and in progress
		elif id == PART1:
				step=int(st.get("step"))
				Orim=int(st.get("Orim"))
				Racoy=int(st.get("Racoy"))
				Perkiron=int(st.get("Perkiron"))
				Manakia=int(st.get("Manakia"))
				#Somak 
				if npcId == NPC[3]:
					if Orim == 6 and Racoy == 11 and Perkiron == 8 and Manakia == 7:  				# Step 1 finished
						htmltext = "7510-07.htm"
						st.takeItems(BRAKIS_REMAINS1_ID,1)
						st.takeItems(HERMODTS_REMAINS1_ID,1)
						st.takeItems(KIRUNAS_REMAINS1_ID,1)
						st.takeItems(TONARS_REMAINS1_ID,1)
						st.giveItems(VENDETTA_TOTEM_ID,1)
						st.setState(PART2)
					else:																				# shows you again his List
						htmltext = "7510-06.htm"
				# Orim and his Part, he sends you out to hunt Portas, Mordeos and Excuros
				elif npcId == NPC[5]:
					if Orim == 1:
						htmltext = "7630-01.htm"
					elif Orim in [2,3,4]:
						htmltext = "7630-05.htm"
					elif Orim == 5:
						htmltext = "7630-06.htm"
						st.takeItems(ORIMS_CONTRACT_ID,-1)
						st.takeItems(PORTAS_EYE_ID,-1)
						st.takeItems(EXCUROS_SCALE_ID,-1)
						st.takeItems(MORDEOS_TALON_ID,-1)
						st.giveItems(BRAKIS_REMAINS1_ID,1)
						st.set("Orim","6")
					else:
						htmltext = "7630-07.htm"
				# Racyos Part he sends you into the church and then to the wastelands... after wastelands he give you his item			
				elif npcId == NPC[2]:
					if Racoy == 1:
						htmltext = "7507-01.htm"
					elif Racoy == 2:
						htmltext = "7507-03.htm"
					elif Racoy == 3:
						htmltext = "7507-04.htm"
					elif 10 > Racoy > 3:
						htmltext = "7507-05.htm"
					elif Racoy == 10:
						htmltext = "7507-06.htm"
						st.takeItems(RACOYS_TOTEM_ID,-1)
						st.takeItems(KIRUNAS_SKULL_ID,-1)
						st.takeItems(KIRUNAS_RIB_BONE_ID,-1)
						st.takeItems(KIRUNAS_SPINE_ID,-1)
						st.takeItems(KIRUNAS_ARM_BONE_ID,-1)
						st.takeItems(KIRUNAS_THIGH_BONE_ID,-1)
						st.takeItems(INSECT_DIAGRAM_BOOK_ID,-1)
						st.giveItems(KIRUNAS_REMAINS1_ID,1)
						st.set("Racoy","11")
					else:
						htmltext = "7507-07.htm"
				# Racoy Part, lady in the church (Viviana)
				elif npcId == NPC[0]:
					if Racoy == 2:								# explainations
						htmltext = "7030-01.htm"
					elif Racoy == 3:							# go to sarien, hurry up
						htmltext = "7030-05.htm"
					elif 10 > Racoy > 3: 						# bring more
						htmltext = "7030-06.htm"
					elif Racoy in [10,11]: 					# this part is finished, for this npc
						htmltext = "7030-07.htm"
				# Racoy Part, Wastelands Trader Sarien tells: "Hunt noble ant leaders and bring the items to Racoy"
				elif npcId == NPC[1]:
					if Racoy == 3:								# explanation about hunting noble ants
						htmltext = "7436-01.htm"
						st.giveItems(INSECT_DIAGRAM_BOOK_ID,1)
						st.takeItems(VIVIANTES_LETTER_ID,1)
						st.set("Racoy","4")
					elif 10 > Racoy > 3: 						# bring more
						htmltext = "7436-02.htm"
					elif Racoy in [10,11]: 					# this part is finished, for this npc
						htmltext = "7436-03.htm"
				# Perkirons Part, just hunt Lizzardsman near Oren		
				elif npcId == NPC[7]:
					if Perkiron == 1:							# explanation
						htmltext = "7682-01.htm"
					elif Perkiron in [2,3,4,5,6]:				# bring more
						htmltext = "7682-03.htm"
					elif Perkiron == 7:						# ah you got anything i need
						htmltext = "7682-04.htm"
						st.takeItems(PEKIRONS_TOTEM_ID,1)
						st.takeItems(TONARS_SKULL_ID,1)
						st.takeItems(TONARS_RIB_BONE_ID,1)
						st.takeItems(TONARS_SPINE_ID,1)
						st.takeItems(TONARS_ARM_BONE_ID,1)
						st.takeItems(TONARS_THIGH_BONE_ID,1)
						st.giveItems(TONARS_REMAINS1_ID,1)
						st.set("Perkiron","8")
					else:										# part is finished for this npc
						htmltext = "7682-05.htm"
				# Manakias Part, hunt Medusas Steona Gorgogon Queen
				elif npcId == NPC[4]:
						if Manakia == 1:														# explanation
							htmltext = "7515-01.htm"
						elif Manakia == 7:														# this part is finished for this npc
							htmltext = "7515-05.htm"
						elif Manakia == 6 and int(st.get("Manakia_Queen"))==3:				# ah you got both items i need
							htmltext = "7515-04.htm"
							st.takeItems(MANAKIAS_TOTEM_ID,1)
							st.takeItems(HERMODTS_SKULL_ID,1)
							st.takeItems(HERMODTS_RIB_BONE_ID,1)
							st.takeItems(HERMODTS_SPINE_ID,1)
							st.takeItems(HERMODTS_ARM_BONE_ID,1)
							st.takeItems(HERMODTS_THIGH_BONE_ID,1)
							st.giveItems(HERMODTS_REMAINS1_ID,1)
							st.set("Manakia","7")	
						else:																	# bring me more, because two vars are required , Manakia and Manakia_Queen
							htmltext = "7515-03.htm"
		elif id == PART2:
				step=int(st.get("step"))
				if npcId == NPC[3]:																
					if step == 1:																# explain Part 2 again or bring more skulls
						htmltext = "7510-08.htm"
					elif step == 2:																# ah you got the items i need
						htmltext = "7510-09.htm"
						st.takeItems(VENDETTA_TOTEM_ID,1)
						st.takeItems(TAMLIN_ORC_HEAD_ID,st.getQuestItemsCount(TAMLIN_ORC_HEAD_ID))
						st.giveItems(WARSPIRIT_TOTEM_ID,1)
						st.giveItems(BRAKIS_REMAINS2_ID,1)
						st.giveItems(HERMODTS_REMAINS2_ID,1)
						st.giveItems(KIRUNAS_REMAINS2_ID,1)
						st.giveItems(TONARS_REMAINS2_ID,1)
						st.set("step","3")
					else:															# this part is finished for this npc
						htmltext = "7510-10.htm"
				elif npcId == NPC[6] and step == 3:
					htmltext = "7649-01.htm"										# ah thx.. i will give you the mark of War Spirit
		return htmltext		
				
	def onKill (self,npc,st):
		npcId=npc.getNpcId()
#		[accepted values for this part],variable for the current part from the mob,maxcount,chance in %, items to give(one per kill max)=DROPLIST[npcId]
		value,var,maxcount,chance,itemList=DROPLIST[npcId]
		random=st.getRandom(100)
#		return the current value of the var
		isValue = int(st.get(var))
		if int(st.get(var)) in value and random<chance:
			# special part for Noble Ants
			if npcId in [89,90]:
				if random>70:
					list=0
				elif random>40:
					list=1
				elif random>10 and st.getQuestItemsCount(KIRUNAS_SKULL_ID)==0:
					list=2
				else:
					return
				for item in itemList[list]:
					count = st.getQuestItemsCount(item)
					st.giveItems(item,1)
					if int(st.get(var)) < 9:
						st.set(var,str(isValue+1))
					if st.getQuestItemsCount(KIRUNAS_SKULL_ID) and int(st.get(var))>6:
						st.set(var,"10")
						st.playSound("ItemdSound.quest_middle")
						return
					st.playSound("ItemSound.quest_itemget")
				return
			# Drop part for any other mobs
			else:		
				for item in itemList:
					count = st.getQuestItemsCount(item)
					if count<maxcount:
						st.giveItems(item,1)
						# spawns 5 new medusas around the dead queen *muha*
						if npcId == 5108:
							for i in range(5):
								st.getPcSpawn().addSpawn(158)
						if count == maxcount-1:
							st.playSound("ItemSound.quest_middle")
							st.set(var,str(isValue+1))
						else:
							st.playSound("ItemSound.quest_itemget")
						return




QUEST		= Quest(233,"233_TestOfWarspirit","Test Of Warspirit")
CREATED		= State('Start', QUEST)
PART1		= State('Part1', QUEST)
PART2		= State('Part2', QUEST)
COMPLETED	= State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7510)

CREATED.addTalkId(7510)

for npcId in NPC:
	PART1.addTalkId(npcId)
	PART2.addTalkId(npcId)

for mobId in PART1_MOBS:
	PART1.addKillId(mobId)
	
for mobId in PART2_MOBS:
	PART2.addKillId(mobId)

print "importing quests: 233: Test Of Warspirit"
