# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "408_PathToElvenwizard"

ROGELLIAS_LETTER_ID = 1218
RED_DOWN_ID = 1219
MAGICAL_POWERS_RUBY_ID = 1220
PURE_AQUAMARINE_ID = 1221
APPETIZING_APPLE_ID = 1222
GOLD_LEAVES_ID = 1223
IMMORTAL_LOVE_ID = 1224
AMETHYST_ID = 1225
NOBILITY_AMETHYST_ID = 1226
FERTILITY_PERIDOT_ID = 1229
ETERNITY_DIAMOND_ID = 1230
CHARM_OF_GRAIN_ID = 1272
SAP_OF_WORLD_TREE_ID = 1273
LUCKY_POTPOURI_ID = 1274

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        if st.getPlayer().getClassId().getId() != 0x19 :
          if st.getPlayer().getClassId().getId() == 0x1a :
            htmltext = "30414-02a.htm"
          else:
            htmltext = "30414-03.htm"
        else:
          if st.getPlayer().getLevel()<19 :
            htmltext = "30414-04.htm"
          else:
            if st.getQuestItemsCount(ETERNITY_DIAMOND_ID) != 0 :
              htmltext = "30414-05.htm"
            else:
              st.set("cond","1")
              st.setState(STARTED)
              st.playSound("ItemSound.quest_accept")
              if st.getQuestItemsCount(FERTILITY_PERIDOT_ID) == 0 :
                st.giveItems(FERTILITY_PERIDOT_ID,1)
              htmltext = "30414-06.htm"
    elif event == "408_1" :
          if int(st.get("cond")) != 0 and st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID) != 0 :
            htmltext = "30414-10.htm"
          elif int(st.get("cond")) != 0 and st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID) == 0 and st.getQuestItemsCount(FERTILITY_PERIDOT_ID) != 0 :
            if st.getQuestItemsCount(ROGELLIAS_LETTER_ID) == 0 :
              st.giveItems(ROGELLIAS_LETTER_ID,1)
            htmltext = "30414-07.htm"
            st.set("cond","2")
    elif event == "408_4" :
          if int(st.get("cond")) != 0 and st.getQuestItemsCount(ROGELLIAS_LETTER_ID) != 0 :
            st.takeItems(ROGELLIAS_LETTER_ID,st.getQuestItemsCount(ROGELLIAS_LETTER_ID))
            if st.getQuestItemsCount(CHARM_OF_GRAIN_ID) == 0 :
              st.giveItems(CHARM_OF_GRAIN_ID,1)
            htmltext = "30157-02.htm"
    elif event == "408_2" :
          if int(st.get("cond")) != 0 and st.getQuestItemsCount(PURE_AQUAMARINE_ID) != 0 :
            htmltext = "30414-13.htm"
          elif int(st.get("cond")) != 0 and st.getQuestItemsCount(PURE_AQUAMARINE_ID) == 0 and st.getQuestItemsCount(FERTILITY_PERIDOT_ID) != 0 :
            if st.getQuestItemsCount(APPETIZING_APPLE_ID) == 0 :
              st.giveItems(APPETIZING_APPLE_ID,1)
            htmltext = "30414-14.htm"
    elif event == "408_5" :
          if int(st.get("cond")) != 0 and st.getQuestItemsCount(APPETIZING_APPLE_ID) != 0 :
            st.takeItems(APPETIZING_APPLE_ID,st.getQuestItemsCount(APPETIZING_APPLE_ID))
            if st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID) == 0 :
              st.giveItems(SAP_OF_WORLD_TREE_ID,1)
            htmltext = "30371-02.htm"
    elif event == "408_3" :
          if int(st.get("cond")) != 0 and st.getQuestItemsCount(NOBILITY_AMETHYST_ID) != 0 :
            htmltext = "30414-17.htm"
          elif int(st.get("cond")) != 0 and st.getQuestItemsCount(NOBILITY_AMETHYST_ID) == 0 and st.getQuestItemsCount(FERTILITY_PERIDOT_ID) != 0 :
            if st.getQuestItemsCount(IMMORTAL_LOVE_ID) == 0 :
              st.giveItems(IMMORTAL_LOVE_ID,1)
            htmltext = "30414-18.htm"
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30414 and id != STARTED : return htmltext
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30414 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          htmltext = "30414-01.htm"
        else:
          htmltext = "30414-01.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(ROGELLIAS_LETTER_ID)==0 and st.getQuestItemsCount(APPETIZING_APPLE_ID)==0 and st.getQuestItemsCount(IMMORTAL_LOVE_ID)==0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID)==0 and st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID)==0 and st.getQuestItemsCount(LUCKY_POTPOURI_ID)==0 and st.getQuestItemsCount(FERTILITY_PERIDOT_ID)!=0 and (st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID)==0 or st.getQuestItemsCount(NOBILITY_AMETHYST_ID)==0 or st.getQuestItemsCount(PURE_AQUAMARINE_ID)==0) :
        htmltext = "30414-11.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(ROGELLIAS_LETTER_ID)!=0 :
        htmltext = "30414-08.htm"
   elif npcId == 30157 and int(st.get("cond"))!=0 and st.getQuestItemsCount(ROGELLIAS_LETTER_ID)!=0 :
        htmltext = "30157-01.htm"
   elif npcId == 30157 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID)!=0 and st.getQuestItemsCount(RED_DOWN_ID)<5 :
        htmltext = "30157-03.htm"
   elif npcId == 30157 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID)!=0 and st.getQuestItemsCount(RED_DOWN_ID)>=5 :
        st.takeItems(RED_DOWN_ID,st.getQuestItemsCount(RED_DOWN_ID))
        st.takeItems(CHARM_OF_GRAIN_ID,st.getQuestItemsCount(CHARM_OF_GRAIN_ID))
        if st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID) == 0 :
          st.giveItems(MAGICAL_POWERS_RUBY_ID,1)
        htmltext = "30157-04.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID)!=0 and st.getQuestItemsCount(RED_DOWN_ID)<5 :
        htmltext = "30414-09.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID)!=0 and st.getQuestItemsCount(RED_DOWN_ID)>=5 :
        htmltext = "30414-25.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(APPETIZING_APPLE_ID)!=0 :
        htmltext = "30414-15.htm"
   elif npcId == 30371 and int(st.get("cond"))!=0 and st.getQuestItemsCount(APPETIZING_APPLE_ID)!=0 :
        htmltext = "30371-01.htm"
   elif npcId == 30371 and int(st.get("cond"))!=0 and st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID)!=0 and st.getQuestItemsCount(GOLD_LEAVES_ID)<5 :
        htmltext = "30371-03.htm"
   elif npcId == 30371 and int(st.get("cond"))!=0 and st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID)!=0 and st.getQuestItemsCount(GOLD_LEAVES_ID)>=5 :
        st.takeItems(GOLD_LEAVES_ID,st.getQuestItemsCount(GOLD_LEAVES_ID))
        st.takeItems(SAP_OF_WORLD_TREE_ID,st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID))
        if st.getQuestItemsCount(PURE_AQUAMARINE_ID) == 0 :
          st.giveItems(PURE_AQUAMARINE_ID,1)
        htmltext = "30371-04.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID)!=0 and st.getQuestItemsCount(GOLD_LEAVES_ID)<5 :
        htmltext = "30414-16.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID)!=0 and st.getQuestItemsCount(GOLD_LEAVES_ID)>=5 :
        htmltext = "30414-26.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(IMMORTAL_LOVE_ID)!=0 :
        htmltext = "30414-19.htm"
   elif npcId == 30423 and int(st.get("cond"))!=0 and st.getQuestItemsCount(IMMORTAL_LOVE_ID)!=0 :
        st.takeItems(IMMORTAL_LOVE_ID,st.getQuestItemsCount(IMMORTAL_LOVE_ID))
        if st.getQuestItemsCount(LUCKY_POTPOURI_ID) == 0 :
          st.giveItems(LUCKY_POTPOURI_ID,1)
        htmltext = "30423-01.htm"
   elif npcId == 30423 and int(st.get("cond"))!=0 and st.getQuestItemsCount(LUCKY_POTPOURI_ID)!=0 and st.getQuestItemsCount(AMETHYST_ID)<2 :
        htmltext = "30423-02.htm"
   elif npcId == 30423 and int(st.get("cond"))!=0 and st.getQuestItemsCount(LUCKY_POTPOURI_ID)!=0 and st.getQuestItemsCount(AMETHYST_ID)>=2 :
        st.takeItems(AMETHYST_ID,st.getQuestItemsCount(AMETHYST_ID))
        st.takeItems(LUCKY_POTPOURI_ID,st.getQuestItemsCount(LUCKY_POTPOURI_ID))
        if st.getQuestItemsCount(NOBILITY_AMETHYST_ID) == 0 :
          st.giveItems(NOBILITY_AMETHYST_ID,1)
        htmltext = "30423-03.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(LUCKY_POTPOURI_ID)!=0 and st.getQuestItemsCount(AMETHYST_ID)<2 :
        htmltext = "30414-20.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(LUCKY_POTPOURI_ID)!=0 and st.getQuestItemsCount(AMETHYST_ID)>=2 :
        htmltext = "30414-27.htm"
   elif npcId == 30414 and int(st.get("cond"))!=0 and st.getQuestItemsCount(ROGELLIAS_LETTER_ID)==0 and st.getQuestItemsCount(APPETIZING_APPLE_ID)==0 and st.getQuestItemsCount(IMMORTAL_LOVE_ID)==0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID)==0 and st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID)==0 and st.getQuestItemsCount(LUCKY_POTPOURI_ID)==0 and st.getQuestItemsCount(FERTILITY_PERIDOT_ID)!=0 and st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID)!=0 and st.getQuestItemsCount(NOBILITY_AMETHYST_ID)!=0 and st.getQuestItemsCount(PURE_AQUAMARINE_ID)!=0 :
        st.takeItems(MAGICAL_POWERS_RUBY_ID,st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID))
        st.takeItems(PURE_AQUAMARINE_ID,st.getQuestItemsCount(PURE_AQUAMARINE_ID))
        st.takeItems(NOBILITY_AMETHYST_ID,st.getQuestItemsCount(NOBILITY_AMETHYST_ID))
        st.takeItems(FERTILITY_PERIDOT_ID,st.getQuestItemsCount(FERTILITY_PERIDOT_ID))
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        if st.getQuestItemsCount(ETERNITY_DIAMOND_ID) == 0 :
          st.giveItems(ETERNITY_DIAMOND_ID,1)
        htmltext = "30414-24.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
   npcId = npc.getNpcId()
   if npcId == 20466 :
        st.set("id","0")
        if int(st.get("cond")) != 0 and st.getQuestItemsCount(CHARM_OF_GRAIN_ID) != 0 and st.getQuestItemsCount(RED_DOWN_ID)<5 and st.getRandom(100)<70 :
            st.giveItems(RED_DOWN_ID,1)
            if st.getQuestItemsCount(RED_DOWN_ID) == 5 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 20019 :
        st.set("id","0")
        if int(st.get("cond")) != 0 and st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID) != 0 and st.getQuestItemsCount(GOLD_LEAVES_ID)<5 and st.getRandom(100)<40 :
            st.giveItems(GOLD_LEAVES_ID,1)
            if st.getQuestItemsCount(GOLD_LEAVES_ID) == 5 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 20047 :
        st.set("id","0")
        if int(st.get("cond")) != 0 and st.getQuestItemsCount(LUCKY_POTPOURI_ID) != 0 and st.getQuestItemsCount(AMETHYST_ID)<2 and st.getRandom(100)<40 :
            st.giveItems(AMETHYST_ID,1)
            if st.getQuestItemsCount(AMETHYST_ID) == 2 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(408,qn,"Path To Elvenwizard")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30414)

QUEST.addTalkId(30414)

QUEST.addTalkId(30157)
QUEST.addTalkId(30371)
QUEST.addTalkId(30423)

QUEST.addKillId(20019)
QUEST.addKillId(20466)
QUEST.addKillId(20047)

STARTED.addQuestDrop(30414,ROGELLIAS_LETTER_ID,1)
STARTED.addQuestDrop(20466,RED_DOWN_ID,1)
STARTED.addQuestDrop(30157,CHARM_OF_GRAIN_ID,1)
STARTED.addQuestDrop(30414,APPETIZING_APPLE_ID,1)
STARTED.addQuestDrop(20019,GOLD_LEAVES_ID,1)
STARTED.addQuestDrop(30371,SAP_OF_WORLD_TREE_ID,1)
STARTED.addQuestDrop(30414,IMMORTAL_LOVE_ID,1)
STARTED.addQuestDrop(20047,AMETHYST_ID,1)
STARTED.addQuestDrop(30423,LUCKY_POTPOURI_ID,1)
STARTED.addQuestDrop(30157,MAGICAL_POWERS_RUBY_ID,1)
STARTED.addQuestDrop(30371,PURE_AQUAMARINE_ID,1)
STARTED.addQuestDrop(30423,NOBILITY_AMETHYST_ID,1)
STARTED.addQuestDrop(30414,FERTILITY_PERIDOT_ID,1)

print "importing quests: 408: Path To Elvenwizard"