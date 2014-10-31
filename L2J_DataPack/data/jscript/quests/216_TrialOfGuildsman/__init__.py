# Maked by Mr. Have fun! Version 0.2
#
# Updated by ElgarL
#

print "importing quests: 216: Trial Of Guildsman"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_GUILDSMAN_ID = 3119
VALKONS_RECOMMEND_ID = 3120
MANDRAGORA_BERRY_ID = 3121
ALLTRANS_INSTRUCTIONS_ID = 3122
ALLTRANS_RECOMMEND1_ID = 3123
ALLTRANS_RECOMMEND2_ID = 3124
NORMANS_INSTRUCTIONS_ID = 3125
NORMANS_RECEIPT_ID = 3126
DUNINGS_INSTRUCTIONS_ID = 3127
DUNINGS_KEY_ID = 3128
NORMANS_LIST_ID = 3129
GRAY_BONE_POWDER_ID = 3130
GRANITE_WHETSTONE_ID = 3131
RED_PIGMENT_ID = 3132
BRAIDED_YARN_ID = 3133
JOURNEYMAN_GEM_ID = 3134
PINTERS_INSTRUCTIONS_ID = 3135
AMBER_BEAD_ID = 3136
AMBER_LUMP_ID = 3137
JOURNEYMAN_DECO_BEADS_ID = 3138
JOURNEYMAN_RING_ID = 3139
RP_JOURNEYMAN_RING_ID = 3024
ADENA_ID = 57
RP_AMBER_BEAD_ID = 3025

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmltext = "7103-06.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(VALKONS_RECOMMEND_ID,1)
        st.takeItems(ADENA_ID,2000)
    elif event == "7103_1" :
          htmltext = "7103-04.htm"
    elif event == "7103_2" :
          if st.getQuestItemsCount(ADENA_ID) >= 2000 :
            htmltext = "7103-05.htm"
          else:
            htmltext = "7103-05a.htm"
    elif event == "7103_3" :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            htmltext = "7103-09a.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.addExpAndSp(32000,3900)
            st.takeItems(JOURNEYMAN_RING_ID,st.getQuestItemsCount(JOURNEYMAN_RING_ID))
            st.takeItems(ALLTRANS_INSTRUCTIONS_ID,1)
            st.takeItems(RP_JOURNEYMAN_RING_ID,1)
            st.giveItems(MARK_OF_GUILDSMAN_ID,1)
    elif event == "7103_4" :
            st.addExpAndSp(80933,12250)
            st.giveItems(7562,8)
            htmltext = "7103-09b.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.takeItems(JOURNEYMAN_RING_ID,st.getQuestItemsCount(JOURNEYMAN_RING_ID))
            st.takeItems(ALLTRANS_INSTRUCTIONS_ID,1)
            st.takeItems(RP_JOURNEYMAN_RING_ID,1)
            st.giveItems(MARK_OF_GUILDSMAN_ID,1)
    elif event == "7283_1" :
          htmltext = "7283-03.htm"
          st.giveItems(ALLTRANS_INSTRUCTIONS_ID,1)
          st.takeItems(VALKONS_RECOMMEND_ID,1)
          st.giveItems(RP_JOURNEYMAN_RING_ID,1)
          st.takeItems(MANDRAGORA_BERRY_ID,1)
          st.giveItems(ALLTRANS_RECOMMEND1_ID,1)
          st.giveItems(ALLTRANS_RECOMMEND2_ID,1)
    elif event == "7210_1" :
          htmltext = "7210-02.htm"
    elif event == "7210_2" :
          htmltext = "7210-03.htm"
    elif event == "7210_3" :
          htmltext = "7210-04.htm"
          st.giveItems(NORMANS_INSTRUCTIONS_ID,1)
          st.takeItems(ALLTRANS_RECOMMEND1_ID,1)
          st.giveItems(NORMANS_RECEIPT_ID,1)
    elif event == "7210_4" :
          htmltext = "7210-08.htm"
    elif event == "7210_5" :
          htmltext = "7210-09.htm"
    elif event == "7210_6" :
          htmltext = "7210-10.htm"
          st.takeItems(DUNINGS_KEY_ID,st.getQuestItemsCount(DUNINGS_KEY_ID))
          st.giveItems(NORMANS_LIST_ID,1)
          st.takeItems(NORMANS_INSTRUCTIONS_ID,1)
    elif event == "7688_1" :
          htmltext = "7688-02.htm"
          st.giveItems(DUNINGS_INSTRUCTIONS_ID,1)
          st.takeItems(NORMANS_RECEIPT_ID,1)
    elif event == "7298_1" :
          htmltext = "7298-03.htm"
    elif event == "7298_2" :
          if st.getPlayer().getClassId().getId() == 0x36 :
            htmltext = "7298-04.htm"
            st.giveItems(PINTERS_INSTRUCTIONS_ID,1)
            st.takeItems(ALLTRANS_RECOMMEND2_ID,1)
          else:
            htmltext = "7298-05.htm"
            st.giveItems(RP_AMBER_BEAD_ID,1)
            st.takeItems(ALLTRANS_RECOMMEND2_ID,1)
            st.giveItems(PINTERS_INSTRUCTIONS_ID,1)
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7103 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
          if (st.getPlayer().getClassId().getId() == 0x38 or st.getPlayer().getClassId().getId() == 0x36) :
            if st.getPlayer().getLevel() < 35 :
              htmltext = "7103-02.htm"
              st.exitQuest(1)
            else:
              htmltext = "7103-03.htm"
          else:
            htmltext = "7103-01.htm"
            st.exitQuest(1)
   elif npcId == 7103 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7103 and int(st.get("cond"))>=1 and st.getQuestItemsCount(VALKONS_RECOMMEND_ID)==1 :
        htmltext = "7103-07.htm"
   elif npcId == 7103 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID)==1 :
        if st.getQuestItemsCount(JOURNEYMAN_RING_ID) < 7 :
          htmltext = "7103-08.htm"
        else:
          htmltext = "7103-09.htm"
   elif npcId == 7283 and int(st.get("cond"))>=1 and st.getQuestItemsCount(VALKONS_RECOMMEND_ID)==1 and st.getQuestItemsCount(MANDRAGORA_BERRY_ID)==0 :
        htmltext = "7283-01.htm"
   elif npcId == 7283 and int(st.get("cond"))>=1 and st.getQuestItemsCount(VALKONS_RECOMMEND_ID)==1 and st.getQuestItemsCount(MANDRAGORA_BERRY_ID)==1 :
        htmltext = "7283-02.htm"
   elif npcId == 7283 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID)==1 :
        if st.getQuestItemsCount(JOURNEYMAN_RING_ID) < 7 :
          htmltext = "7283-04.htm"
        else:
          htmltext = "7283-05.htm"
   elif npcId == 7210 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount(ALLTRANS_RECOMMEND1_ID)==1 :
        htmltext = "7210-01.htm"
   elif npcId == 7210 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_RECEIPT_ID) :
        htmltext = "7210-05.htm"
   elif npcId == 7210 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(DUNINGS_INSTRUCTIONS_ID) :
        htmltext = "7210-06.htm"
   elif npcId == 7210 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(DUNINGS_KEY_ID)>=30 :
        htmltext = "7210-07.htm"
   elif npcId == 7210 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_LIST_ID) :
        if st.getQuestItemsCount(GRAY_BONE_POWDER_ID) >= 70 and st.getQuestItemsCount(GRANITE_WHETSTONE_ID) >= 70  :
          htmltext = "7210-12.htm"
          st.takeItems(NORMANS_LIST_ID,1)
          st.takeItems(GRAY_BONE_POWDER_ID,st.getQuestItemsCount(GRAY_BONE_POWDER_ID))
          st.takeItems(GRANITE_WHETSTONE_ID,st.getQuestItemsCount(GRANITE_WHETSTONE_ID))
          st.takeItems(RED_PIGMENT_ID,st.getQuestItemsCount(RED_PIGMENT_ID))
          st.takeItems(BRAIDED_YARN_ID,st.getQuestItemsCount(BRAIDED_YARN_ID))
          st.giveItems(JOURNEYMAN_GEM_ID,7)
        else:
          htmltext = "7210-11.htm"
   elif npcId == 7210 and int(st.get("cond"))>=1 and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) == 0 and st.getQuestItemsCount(NORMANS_LIST_ID) == 0 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID)==1 and (st.getQuestItemsCount(JOURNEYMAN_GEM_ID) or st.getQuestItemsCount(JOURNEYMAN_RING_ID)) :
        htmltext = "7210-13.htm"
   elif npcId == 7688 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_RECEIPT_ID) :
        htmltext = "7688-01.htm"
   elif npcId == 7688 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(DUNINGS_INSTRUCTIONS_ID) :
        htmltext = "7688-03.htm"
   elif npcId == 7688 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(DUNINGS_KEY_ID)>=30 :
        htmltext = "7688-04.htm"
   elif npcId == 7688 and int(st.get("cond"))>=1 and st.getQuestItemsCount(NORMANS_RECEIPT_ID) == 0 and st.getQuestItemsCount(DUNINGS_INSTRUCTIONS_ID) == 0 and st.getQuestItemsCount(DUNINGS_KEY_ID) == 0 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID)==1 :
        htmltext = "7688-01.htm"
   elif npcId == 7298 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(ALLTRANS_RECOMMEND2_ID) :
        if st.getPlayer().getLevel() < 36 :
          htmltext = "7298-01.htm"
        else:
          htmltext = "7298-02.htm"
   elif npcId == 7298 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) and st.getQuestItemsCount(PINTERS_INSTRUCTIONS_ID) :
        if st.getQuestItemsCount(AMBER_BEAD_ID) < 70 :
          htmltext = "7298-06.htm"
        else:
          htmltext = "7298-07.htm"
          st.takeItems(PINTERS_INSTRUCTIONS_ID,1)
          st.takeItems(AMBER_BEAD_ID,st.getQuestItemsCount(AMBER_BEAD_ID))
          st.takeItems(RP_AMBER_BEAD_ID,st.getQuestItemsCount(RP_AMBER_BEAD_ID))
          st.takeItems(AMBER_LUMP_ID,st.getQuestItemsCount(AMBER_LUMP_ID))
          st.giveItems(JOURNEYMAN_DECO_BEADS_ID,7)
   elif npcId == 7298 and int(st.get("cond"))>=1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID)==1 and st.getQuestItemsCount(PINTERS_INSTRUCTIONS_ID)==0 and (st.getQuestItemsCount(JOURNEYMAN_DECO_BEADS_ID) or st.getQuestItemsCount(JOURNEYMAN_RING_ID)) :
        htmltext = "7298-08.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 223 :
     st.set("id","0")
     if int(st.get("cond")) >= 1 and st.getQuestItemsCount(VALKONS_RECOMMEND_ID) == 1 and st.getQuestItemsCount(MANDRAGORA_BERRY_ID) == 0 :
       if st.getRandom(100) <= 20 :
         st.giveItems(MANDRAGORA_BERRY_ID,1)
         st.playSound("ItemSound.quest_middle")
   elif npcId == 154 or npcId == 155 or npcId == 156:
     st.set("id","0")
     if int(st.get("cond")) >= 1 and st.getQuestItemsCount(VALKONS_RECOMMEND_ID) == 1 and st.getQuestItemsCount(MANDRAGORA_BERRY_ID) == 0 :
       if st.getRandom(100) <= 50 :
        st.giveItems(MANDRAGORA_BERRY_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 267 or npcId == 268 or npcId == 271 or npcId == 269 or npcId == 270:
    st.set("id","0")
    if int(st.get("cond")) >= 1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(NORMANS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(DUNINGS_INSTRUCTIONS_ID) == 1 :
     if st.getRandom(100) <= 30 and st.getQuestItemsCount(DUNINGS_KEY_ID) <= 29 :
      if st.getQuestItemsCount(DUNINGS_KEY_ID) == 29 :
        st.giveItems(DUNINGS_KEY_ID,1)
        st.takeItems(DUNINGS_INSTRUCTIONS_ID,1)
        st.playSound("ItemSound.quest_middle")
      else:
        st.giveItems(DUNINGS_KEY_ID,1)
        st.playSound("ItemSound.quest_itemget")
   elif npcId == 201 or npcId == 200:
    st.set("id","0")
    if int(st.get("cond")) >= 1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(NORMANS_LIST_ID) == 1 and st.getQuestItemsCount(GRAY_BONE_POWDER_ID) <= 68 :
     if st.getQuestItemsCount(GRAY_BONE_POWDER_ID) == 68 :
      st.giveItems(GRAY_BONE_POWDER_ID,2)
      st.playSound("ItemSound.quest_middle")
     else:
      st.giveItems(GRAY_BONE_POWDER_ID,2)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 83 :
    st.set("id","0")
    if int(st.get("cond")) >= 1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(NORMANS_LIST_ID) == 1 and st.getQuestItemsCount(GRANITE_WHETSTONE_ID) <= 68 :
     if st.getQuestItemsCount(GRANITE_WHETSTONE_ID) == 68 :
      st.giveItems(GRANITE_WHETSTONE_ID,2)
      st.playSound("ItemSound.quest_middle")
     else:
      st.giveItems(GRANITE_WHETSTONE_ID,2)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 202 :
    st.set("id","0")
    if int(st.get("cond")) >= 1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(NORMANS_LIST_ID) == 1 and st.getQuestItemsCount(RED_PIGMENT_ID) <= 68 :
     if st.getQuestItemsCount(RED_PIGMENT_ID) == 68 :
      st.giveItems(RED_PIGMENT_ID,2)
      st.playSound("ItemSound.quest_middle")
     else:
      st.giveItems(RED_PIGMENT_ID,2)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 168 :
    st.set("id","0")
    if int(st.get("cond")) >= 1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(NORMANS_LIST_ID) == 1 and st.getQuestItemsCount(BRAIDED_YARN_ID) <= 68 :
     if st.getQuestItemsCount(BRAIDED_YARN_ID) == 68 :
      st.giveItems(BRAIDED_YARN_ID,2)
      st.playSound("ItemSound.quest_middle")
     else:
      st.giveItems(BRAIDED_YARN_ID,2)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 79 or npcId == 81 or npcId == 80 :
    st.set("id","0")
    if int(st.get("cond")) >= 1 and st.getQuestItemsCount(ALLTRANS_INSTRUCTIONS_ID) == 1 and st.getQuestItemsCount(PINTERS_INSTRUCTIONS_ID) == 1 :
     if st.getQuestItemsCount(AMBER_BEAD_ID) < 70 :
      if st.getRandom(100) < 50 and st.getPlayer().getClassId().getId() == 0x36: #and IsSpoiled() == 1 :
        st.giveItems(AMBER_BEAD_ID,1)
        st.playSound("Itemsound.quest_itemget")
      if st.getRandom(100) < 50 :
        if st.getQuestItemsCount(AMBER_BEAD_ID) < 69 :
          st.giveItems(AMBER_BEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
        else:
          st.giveItems(AMBER_BEAD_ID,1)
          st.playSound("ItemSound.quest_middle")

   return

QUEST       = Quest(216,"216_TrialOfGuildsman","Trial Of Guildsman")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7103)

STARTING.addTalkId(7103)

STARTED.addTalkId(7103)
STARTED.addTalkId(7210)
STARTED.addTalkId(7283)
STARTED.addTalkId(7298)
STARTED.addTalkId(7688)

STARTED.addKillId(154)
STARTED.addKillId(155)
STARTED.addKillId(156)
STARTED.addKillId(168)
STARTED.addKillId(200)
STARTED.addKillId(201)
STARTED.addKillId(202)
STARTED.addKillId(223)
STARTED.addKillId(267)
STARTED.addKillId(268)
STARTED.addKillId(269)
STARTED.addKillId(270)
STARTED.addKillId(271)
STARTED.addKillId(79)
STARTED.addKillId(80)
STARTED.addKillId(81)
STARTED.addKillId(83)

STARTED.addQuestDrop(7283,RP_JOURNEYMAN_RING_ID,1)
STARTED.addQuestDrop(7283,ALLTRANS_INSTRUCTIONS_ID,1)
STARTED.addQuestDrop(7283,RP_JOURNEYMAN_RING_ID,1)
STARTED.addQuestDrop(7103,VALKONS_RECOMMEND_ID,1)
STARTED.addQuestDrop(223,MANDRAGORA_BERRY_ID,1)
STARTED.addQuestDrop(154,MANDRAGORA_BERRY_ID,1)
STARTED.addQuestDrop(155,MANDRAGORA_BERRY_ID,1)
STARTED.addQuestDrop(156,MANDRAGORA_BERRY_ID,1)
STARTED.addQuestDrop(7283,ALLTRANS_RECOMMEND1_ID,1)
STARTED.addQuestDrop(267,DUNINGS_KEY_ID,1)
STARTED.addQuestDrop(268,DUNINGS_KEY_ID,1)
STARTED.addQuestDrop(271,DUNINGS_KEY_ID,1)
STARTED.addQuestDrop(269,DUNINGS_KEY_ID,1)
STARTED.addQuestDrop(270,DUNINGS_KEY_ID,1)
STARTED.addQuestDrop(7210,NORMANS_INSTRUCTIONS_ID,1)
STARTED.addQuestDrop(7210,NORMANS_LIST_ID,1)
STARTED.addQuestDrop(7210,NORMANS_RECEIPT_ID,1)
STARTED.addQuestDrop(7283,ALLTRANS_RECOMMEND2_ID,1)
STARTED.addQuestDrop(7298,PINTERS_INSTRUCTIONS_ID,1)
STARTED.addQuestDrop(7298,RP_AMBER_BEAD_ID,1)
STARTED.addQuestDrop(79,AMBER_BEAD_ID,1)
STARTED.addQuestDrop(81,AMBER_BEAD_ID,1)
STARTED.addQuestDrop(80,AMBER_BEAD_ID,1)
STARTED.addQuestDrop(7298,RP_AMBER_BEAD_ID,1)
STARTED.addQuestDrop(7688,DUNINGS_INSTRUCTIONS_ID,1)
