# version 0.2
# by DrLecter, with fixes from Ryo_Saeba

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

# constants section

REQUIRED_SPIDER_LEGS = 50
#Quest items
ANIMAL_LOVERS_LIST1 = 3417

ANIMAL_SLAYER_LIST1 = 3418
ANIMAL_SLAYER_LIST2 = 3419
ANIMAL_SLAYER_LIST3 = 3420
ANIMAL_SLAYER_LIST4 = 3421
ANIMAL_SLAYER_LIST5 = 3422

SPIDER_LEG1 = 3423
SPIDER_LEG2 = 3424
SPIDER_LEG3 = 3425
SPIDER_LEG4 = 3426
SPIDER_LEG5 = 3427
#Chance of drop in %
SPIDER_LEG_DROP = 100
#mobs
#1 humans
SPIDER_H1 = 103 #Giant Spider
SPIDER_H2 = 106 #Talon Spider
SPIDER_H3 = 108 #Blade Spider
#2 elves
SPIDER_LE1 = 460 # Crimson Spider
SPIDER_LE2 = 308 # Hook Spider
SPIDER_LE3 = 466 # Pincer Spider
#3 dark elves
SPIDER_DE1 =  25 # Lesser Dark Horror
SPIDER_DE2 = 105 # Dark Horror 
SPIDER_DE3 =  34 # Prowler
#4 orcs
SPIDER_O1 = 474 # Kasha Spider
SPIDER_O2 = 476 # Kasha Fang Spider
SPIDER_O3 = 478 # Kasha Blade Spider
#5 dwarves
SPIDER_D1 = 403 # Hunter Tarantula
SPIDER_D2 = 508 # Plunder Tarantula

#NPCs
PET_MANAGER_MARTIN = 7731
GK_BELLA = 7256
MC_ELLIE = 7091
GD_METTY = 7072

#Rewards
WOLF_COLLAR = 2375

# helper functions section
def getCount_proof(st) :
  race = st.getPlayer().getRace().ordinal()
  if race == 0: proofs = st.getQuestItemsCount(SPIDER_LEG1)
  if race == 1: proofs = st.getQuestItemsCount(SPIDER_LEG2)
  if race == 2: proofs = st.getQuestItemsCount(SPIDER_LEG3)
  if race == 3: proofs = st.getQuestItemsCount(SPIDER_LEG4)
  if race == 4: proofs = st.getQuestItemsCount(SPIDER_LEG5)
  return proofs

def check_questions(st) :
  question = 1  
  quiz = st.get("quiz")
  answers = int(st.get("answers"))
  if answers < 10 :
    questions = quiz.split()
    index = st.getRandom(len(questions) - 1)
    question = questions[index]
    if len(questions) > 10 - answers :
      questions[index] = questions[-1]
      del questions[-1]
    st.set("quiz"," ".join(questions))
    htmltext = "419_q"+str(question)+".htm"
    return htmltext
  elif answers == 10 :
    st.setState(COMPLETED)
    st.giveItems(WOLF_COLLAR,1)
    st.exitQuest(1)
    st.playSound("ItemSound.quest_finish")
    htmltext="Completed.htm"
  return htmltext
  
# Main Quest Code
class Quest (JQuest):

  def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

  def onEvent (self,event,st):
    id = st.getState()
    if id == CREATED :
      st.set("cond","0")
      if event == "details" :
        return "419_confirm.htm"
      elif event == "agree" :
        st.setState(STARTED)
        st.set("cond","1")
        race = st.getPlayer().getRace().ordinal()
        if race == 0:
           st.giveItems(ANIMAL_SLAYER_LIST1,1)
           htmltext = "419_slay_0.htm"
        if race == 1:
           st.giveItems(ANIMAL_SLAYER_LIST2,1)
           htmltext = "419_slay_1.htm"
        if race == 2:
           st.giveItems(ANIMAL_SLAYER_LIST3,1)
           htmltext = "419_slay_2.htm"
        if race == 3:
           st.giveItems(ANIMAL_SLAYER_LIST4,1)
           htmltext = "419_slay_3.htm"
        if race == 4:
           st.giveItems(ANIMAL_SLAYER_LIST5,1)
           htmltext = "419_slay_4.htm"
        st.playSound("ItemSound.quest_accept")
        return htmltext
      elif event == "disagree" :
        st.exitQuest(1)
        return "419_cancelled.htm"
    elif id == SLAYED :
      if event == "talk"  :
        st.giveItems(ANIMAL_LOVERS_LIST1,1)
        return "419_talk.htm"
      if event == "talk1" :
        return "419_bella_2.htm"
      if event == "talk2" :
        st.set("progress", str(int(st.get("progress")) | 1))
        return "419_bella_3.htm"
      if event == "talk3" :
        st.set("progress", str(int(st.get("progress")) | 2))
        return "419_ellie_2.htm"
      if event == "talk4" :
        st.set("progress", str(int(st.get("progress")) | 4))
        return "419_metty_2.htm"
    elif id == TALKED :
      if event == "tryme" :
        return check_questions(st) 
      elif event == "wrong" :
        st.setState(SLAYED)
        st.set("progress","0")
        st.unset("quiz")
        st.unset("answers")
        st.giveItems(ANIMAL_LOVERS_LIST1,1)
        return "419_failed.htm"
      elif event == "right" :
        st.set("answers",str(int(st.get("answers")) + 1))
        return check_questions(st)
    return

  def onTalk (self,npc,st):
    npcid = npc.getNpcId()
    id = st.getState()
    if npcid == PET_MANAGER_MARTIN :
      if id == CREATED  :
         if st.getPlayer().getLevel() < 15 :
            st.exitQuest(1)
            return "419_low_level.htm"
         return "Start.htm"
      if id == STARTED  :
         if getCount_proof(st) == 0 :
            return "419_no_slay.htm"  
         elif getCount_proof(st) < REQUIRED_SPIDER_LEGS :
            return "419_pending_slay.htm"
         else :
            st.setState(SLAYED)
            st.clearQuestDrops()
            st.set("progress","0")
            race = st.getPlayer().getRace().ordinal()
            if race == 0:
                st.takeItems(SPIDER_LEG1,REQUIRED_SPIDER_LEGS)
                st.takeItems(ANIMAL_SLAYER_LIST1,1)
            if race == 1:
                st.takeItems(SPIDER_LEG2,REQUIRED_SPIDER_LEGS)
                st.takeItems(ANIMAL_SLAYER_LIST2,1)
            if race == 2:
                st.takeItems(SPIDER_LEG3,REQUIRED_SPIDER_LEGS)
                st.takeItems(ANIMAL_SLAYER_LIST3,1)
            if race == 3:
                st.takeItems(SPIDER_LEG4,REQUIRED_SPIDER_LEGS)
                st.takeItems(ANIMAL_SLAYER_LIST4,1)
            if race == 4:
                st.takeItems(SPIDER_LEG5,REQUIRED_SPIDER_LEGS)
                st.takeItems(ANIMAL_SLAYER_LIST5,1)
            return "Slayed.htm"
      if id == SLAYED :
        if int(st.get("progress")) == 7 :
           st.takeItems(ANIMAL_LOVERS_LIST1,1)
           st.setState(TALKED)
           st.set("quiz","1 2 3 4 5 6 7 8 9 10 11 12 13 14")
           st.set("answers","0")
           return "Talked.htm"
        return "419_pending_talk.htm"
    elif id == SLAYED:
      if npcid == GK_BELLA :
         return "419_bella_1.htm"
      elif npcid == MC_ELLIE :
         return "419_ellie_1.htm"
      elif npcid == GD_METTY :
         return "419_metty_1.htm"
    return

  def onKill (self,npc,st):

      npcId = npc.getNpcId()
      collected = getCount_proof(st)
      if collected < REQUIRED_SPIDER_LEGS:
         race = st.getPlayer().getRace().ordinal()
         if race == 0 :
            npcs = [ SPIDER_H1, SPIDER_H2, SPIDER_H3 ]
            item = SPIDER_LEG1
         if race == 1 :
            npcs = [ SPIDER_LE1, SPIDER_LE2, SPIDER_LE3 ]
            item = SPIDER_LEG2
         if race == 2 :
            npcs = [ SPIDER_DE1, SPIDER_DE2, SPIDER_DE3 ]
            item = SPIDER_LEG3
         if race == 3 :
            npcs = [ SPIDER_O1, SPIDER_O2, SPIDER_O3 ]
            item = SPIDER_LEG4
         if race == 4 :
            npcs = [ SPIDER_D1, SPIDER_D2 ]
            item = SPIDER_LEG5
         if npcId in npcs :
            if st.getRandom(100) < SPIDER_LEG_DROP :
               st.giveItems(item,1)
               if collected + 1 < REQUIRED_SPIDER_LEGS :
                  st.playSound("ItemSound.quest_itemget")
               else :
                  st.playSound("ItemSound.quest_middle")
      return

# Quest class and state definition
QUEST       = Quest(419, "419_GetAPet", "Wolf Collar")
CREATED     = State('Start',       QUEST)
STARTED     = State('Started',     QUEST)
SLAYED      = State('Slayed',      QUEST)
TALKED      = State('Talked',     QUEST)
COMPLETED   = State('Completed',   QUEST)

# Quest initialization
QUEST.setInitialState(CREATED)
# Quest NPC starter initialization
QUEST.addStartNpc(PET_MANAGER_MARTIN)

# Quest Item Drop initialization
STARTED.addQuestDrop(SPIDER_H1,SPIDER_LEG1,1)
STARTED.addQuestDrop(SPIDER_H2,SPIDER_LEG1,1)
STARTED.addQuestDrop(SPIDER_H3,SPIDER_LEG1,1)

STARTED.addQuestDrop(SPIDER_LE1,SPIDER_LEG2,1)
STARTED.addQuestDrop(SPIDER_LE2,SPIDER_LEG2,1)
STARTED.addQuestDrop(SPIDER_LE3,SPIDER_LEG2,1)

STARTED.addQuestDrop(SPIDER_DE1,SPIDER_LEG3,1)
STARTED.addQuestDrop(SPIDER_DE2,SPIDER_LEG3,1)
STARTED.addQuestDrop(SPIDER_DE3,SPIDER_LEG3,1)

STARTED.addQuestDrop(SPIDER_O1,SPIDER_LEG4,1)
STARTED.addQuestDrop(SPIDER_O2,SPIDER_LEG4,1)
STARTED.addQuestDrop(SPIDER_O3,SPIDER_LEG4,1)

STARTED.addQuestDrop(SPIDER_D1,SPIDER_LEG5,1)
STARTED.addQuestDrop(SPIDER_D2,SPIDER_LEG5,1)

# Quest mob initialization
STARTED.addKillId(SPIDER_H1)
STARTED.addKillId(SPIDER_H2)
STARTED.addKillId(SPIDER_H3)

STARTED.addKillId(SPIDER_LE1)
STARTED.addKillId(SPIDER_LE2)
STARTED.addKillId(SPIDER_LE3)

STARTED.addKillId(SPIDER_DE1)
STARTED.addKillId(SPIDER_DE2)
STARTED.addKillId(SPIDER_DE3)

STARTED.addKillId(SPIDER_O1)
STARTED.addKillId(SPIDER_O2)
STARTED.addKillId(SPIDER_O3)

STARTED.addKillId(SPIDER_D1)
STARTED.addKillId(SPIDER_D2)

# Quest NPC initialization
CREATED.addTalkId(PET_MANAGER_MARTIN)
STARTED.addTalkId(PET_MANAGER_MARTIN)
SLAYED.addTalkId(PET_MANAGER_MARTIN)
TALKED.addTalkId(PET_MANAGER_MARTIN)

SLAYED.addTalkId(GK_BELLA)
SLAYED.addTalkId(MC_ELLIE)
SLAYED.addTalkId(GD_METTY)

print "importing quests: 419: Get a Pet"