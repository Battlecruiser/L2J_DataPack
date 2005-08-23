# Maked by Mr. Have fun! Version 0.2
print "importing quests: 420: Little Wings"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

Q_FAIRY_STONE_ID = 3816
Q_FAIRY_STONE_DELUX_ID = 3817
Q_LIST_OF_STUFF_FOR_FS_ID = 3818
Q_LIST_OF_STUFF_FOR_FSD_ID = 3819
COAL_ID = 1567
CHARCOAL_ID = 1871
GEMSTONE_D_ID = 2130
SILVER_NUGGET_ID = 1873
Q_INPICIOS_BACK_SKIN_ID = 3820
GEMSTONE_C_ID = 2131
STONE_OF_PURITY_ID = 1875
Q_FAIRY_DUST_ID = 3499
Q_JUICE_OF_MONKSHOOD_ID = 3821
HATCHLINGS_SOFT_LEATHER_ID = 3912
FOOD_FOR_HATCHLING_ID = 4038
Q_EGG_OF_DRAKE_EXARION_ID = 3823
Q_SCALE_OF_DRAKE_EXARION_ID = 3822
Q_EGG_OF_DRAKE_ZWOV_ID = 3825
Q_SCALE_OF_DRAKE_ZWOV_ID = 3824
Q_SCALE_OF_DRAKE_KALIBRAN_ID = 3826
Q_EGG_OF_DRAKE_KALIBRAN_ID = 3827
Q_EGG_OF_WYRM_SUZET_ID = 3829
Q_SCALE_OF_WYRM_SUZET_ID = 3828
Q_EGG_OF_WYRM_SHAMHAI_ID = 3831
Q_SCALE_OF_WYRM_SHAMHAI_ID = 3830

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7829-02.htm"
      st.set("cond","1")
    elif event == "7610_1" :
      htmltext = "7610-02.htm"
    elif event == "7610_2" :
      htmltext = "7610-03.htm"
    elif event == "7610_3" :
      htmltext = "7610-04.htm"
    elif event == "7610_4" :
      st.giveItems(Q_LIST_OF_STUFF_FOR_FS_ID,1)
      st.set("cond","2")
      htmltext = "7610-05.htm"
    elif event == "7610_5" :
      st.giveItems(Q_LIST_OF_STUFF_FOR_FSD_ID,1)
      st.set("cond","2")
      htmltext = "7610-06.htm"
    elif event == "7610_6" :
      st.giveItems(Q_LIST_OF_STUFF_FOR_FS_ID,1)
      st.set("cond","11")
      htmltext = "7610-12.htm"
    elif event == "7610_7" :
      st.giveItems(Q_LIST_OF_STUFF_FOR_FSD_ID,1)
      st.set("cond","11")
      htmltext = "7610-13.htm"
    elif event == "7711_1" :
      htmltext = "7711-02.htm"
    elif event == "7711_2" :
      if st.getQuestItemsCount(Q_FAIRY_STONE_ID) == 1 :
        st.set("cond","4")
        htmltext = "7711-03.htm"
      else:
        st.set("cond","4")
        htmltext = "7711-04.htm"
    elif event == "7608_1" :
        st.takeItems(Q_LIST_OF_STUFF_FOR_FS_ID,1)
        st.takeItems(COAL_ID,10)
        st.takeItems(CHARCOAL_ID,10)
        st.takeItems(GEMSTONE_D_ID,1)
        st.takeItems(SILVER_NUGGET_ID,3)
        st.takeItems(Q_INPICIOS_BACK_SKIN_ID,st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID))
        st.giveItems(Q_FAIRY_STONE_ID,1)
        htmltext = "7608-03.htm"
    elif event == "7608_2" :
        st.takeItems(Q_LIST_OF_STUFF_FOR_FSD_ID,1)
        st.takeItems(COAL_ID,10)
        st.takeItems(CHARCOAL_ID,10)
        st.takeItems(GEMSTONE_C_ID,1)
        st.takeItems(STONE_OF_PURITY_ID,1)
        st.takeItems(SILVER_NUGGET_ID,5)
        st.takeItems(Q_INPICIOS_BACK_SKIN_ID,st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID))
        st.giveItems(Q_FAIRY_STONE_DELUX_ID,1)
        htmltext = "7608-05.htm"
    elif event == "7747_1" :
        st.takeItems(Q_FAIRY_STONE_ID,1)
        st.set("cond","5")
        htmltext = "7747-03.htm"
    elif event == "7747_3" :
      st.giveItems(Q_FAIRY_DUST_ID,1)
      st.takeItems(Q_FAIRY_STONE_DELUX_ID,1)
      st.set("cond","5")
      htmltext = "7747-05.htm"
    elif event == "7747_2" :
      htmltext = "7747-06.htm"
    elif event == "7747_4" :
      st.giveItems(Q_JUICE_OF_MONKSHOOD_ID,1)
      st.set("cond","6")
      htmltext = "7747-08.htm"
    elif event == "7747_5" :
      if st.getQuestItemsCount(Q_FAIRY_DUST_ID) == 1 :
        htmltext = "7747-13.htm"
      else:
        if st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          htmltext = "7747-16.htm"
    elif event == "7747_7" :
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      htmltext = "7747-14.htm"
    elif event == "7747_6" :
#      if st.getGameTicks() != int(st.get("id")) :
#        st.set("id",str(st.getGameTicks()))
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        if st.getQuestItemsCount(Q_FAIRY_DUST_ID) == 0 :
          htmltext = "7747-14.htm"
        else:
          if int(st.get("id")) < 5 :
            htmltext = "7747-15.htm"
            st.giveItems(HATCHLINGS_SOFT_LEATHER_ID,1)
          else:
            htmltext = "7747-15t.htm"
            st.giveItems(FOOD_FOR_HATCHLING_ID,20)
            st.takeItems(Q_FAIRY_DUST_ID,1)
    elif event == "7748_1" :
        st.giveItems(Q_SCALE_OF_DRAKE_EXARION_ID,1)
        st.takeItems(Q_JUICE_OF_MONKSHOOD_ID,1)
        st.set("cond","7")
        htmltext = "7748-03.htm"
    elif event == "7749_1" :
        st.giveItems(Q_SCALE_OF_DRAKE_ZWOV_ID,1)
        st.takeItems(Q_JUICE_OF_MONKSHOOD_ID,1)
        st.set("cond","7")
        htmltext = "7749-03.htm"
    elif event == "7750_1" :
        st.giveItems(Q_SCALE_OF_DRAKE_KALIBRAN_ID,1)
        st.takeItems(Q_JUICE_OF_MONKSHOOD_ID,1)
        st.set("cond","7")
        htmltext = "7750-03.htm"
    elif event == "7750_2" :
        st.takeItems(Q_EGG_OF_DRAKE_KALIBRAN_ID,st.getQuestItemsCount(Q_EGG_OF_DRAKE_KALIBRAN_ID))
        st.takeItems(Q_SCALE_OF_DRAKE_KALIBRAN_ID,1)
        st.giveItems(Q_EGG_OF_DRAKE_KALIBRAN_ID,1)
        st.set("cond","8")
        htmltext = "7750-06.htm"
    elif event == "7751_1" :
        htmltext = "7751-03.htm"
    elif event == "7751_2" :
        st.giveItems(Q_SCALE_OF_WYRM_SUZET_ID,1)
        st.takeItems(Q_JUICE_OF_MONKSHOOD_ID,1)
        st.set("cond","7")
        htmltext = "7751-04.htm"
    elif event == "7752_1" :
        st.giveItems(Q_SCALE_OF_WYRM_SHAMHAI_ID,1)
        st.takeItems(Q_JUICE_OF_MONKSHOOD_ID,1)
        st.set("cond","7")
        htmltext = "7752-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7829 and int(st.get("cond"))==0 and st.getPlayer().getLevel() >= 35 :
     if int(st.get("cond")) < 15 :
       htmltext = "7829-01.htm"
       st.set("cond","1")
       return htmltext
     else:
       htmltext = "7829-01.htm"
   elif npcId == 7829 and int(st.get("cond"))==0 and st.getPlayer().getLevel() < 35 :
       htmltext = "7829-03.htm"
   elif npcId == 7829 and int(st.get("cond"))==1 :
       htmltext = "7829-04.htm"
   elif npcId == 7610 and int(st.get("cond"))==1 :
       htmltext = "7610-01.htm"
   elif npcId == 7610 and (int(st.get("cond"))==2 or int(st.get("cond"))==11) and st.getQuestItemsCount(Q_FAIRY_STONE_ID)==0 and st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==0 :
       htmltext = "7610-07.htm"
   elif npcId == 7610 and int(st.get("cond"))==2 and (st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 or st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1) :
      st.set("cond","3")
      htmltext = "7610-08.htm"
   elif npcId == 7610 and int(st.get("cond"))==11 and (st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 or st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1) :
      htmltext = "7610-14.htm"
   elif npcId == 7610 and int(st.get("cond"))==3 :
      htmltext = "7610-09.htm"
   elif npcId == 7610 and int(st.get("cond"))==10 :
      htmltext = "7610-10.htm"
   elif npcId == 7610 and int(st.get("cond"))==4 and (st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 or st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1) :
      htmltext = "7610-11.htm"
   elif npcId == 7711 and int(st.get("cond"))==3 and (st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 or st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1) :
      htmltext = "7711-01.htm"
   elif npcId == 7711 and int(st.get("cond"))==11 and st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 :
      st.set("cond","4")
      htmltext = "7711-05.htm"
   elif npcId == 7711 and int(st.get("cond"))==11 and st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1 :
      st.set("cond","4")
      htmltext = "7711-06.htm"
   elif npcId == 7711 and int(st.get("cond"))==4 and st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 :
      htmltext = "7711-07.htm"
   elif npcId == 7711 and int(st.get("cond"))==4 and st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1 :
      htmltext = "7711-08.htm"
   elif npcId == 7711 and int(st.get("cond"))==10 :
      htmltext = "7711-09.htm"
   elif npcId == 7711 and int(st.get("cond"))==11 and st.getQuestItemsCount(Q_FAIRY_STONE_ID)==0 and st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==0 and (st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FS_ID)==1 or st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FSD_ID)==1) :
      htmltext = "7711-10.htm"
   elif npcId == 7608 and (int(st.get("cond"))==2 or int(st.get("cond"))==11) and ((st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FS_ID)==1 and (st.getQuestItemsCount(COAL_ID)<10 or st.getQuestItemsCount(CHARCOAL_ID)<10 or st.getQuestItemsCount(GEMSTONE_D_ID)==0 or st.getQuestItemsCount(SILVER_NUGGET_ID)<3 or st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID)<10)) or (st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FSD_ID)==1 and (st.getQuestItemsCount(COAL_ID)<10 or st.getQuestItemsCount(CHARCOAL_ID)<10 or st.getQuestItemsCount(GEMSTONE_C_ID)==0 or st.getQuestItemsCount(STONE_OF_PURITY_ID)==0 or st.getQuestItemsCount(SILVER_NUGGET_ID)<5 or st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID)<20))) :
      htmltext = "7608-01.htm"
   elif npcId == 7608 and (int(st.get("cond"))==2 or int(st.get("cond"))==11) and st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FS_ID)==1 and st.getQuestItemsCount(COAL_ID)>=10 and st.getQuestItemsCount(CHARCOAL_ID)>=10 and st.getQuestItemsCount(GEMSTONE_D_ID)>=1 and st.getQuestItemsCount(SILVER_NUGGET_ID)>=3 and st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID)>=10 :
      htmltext = "7608-02.htm"
   elif npcId == 7608 and (int(st.get("cond"))==2 or int(st.get("cond"))==11) and st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FSD_ID)==1 and st.getQuestItemsCount(COAL_ID)>=10 and st.getQuestItemsCount(CHARCOAL_ID)>=10 and st.getQuestItemsCount(GEMSTONE_C_ID)>=1 and st.getQuestItemsCount(STONE_OF_PURITY_ID)>=1 and st.getQuestItemsCount(SILVER_NUGGET_ID)>=5 and st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID)>=20 :
      htmltext = "7608-04.htm"
   elif npcId == 7608 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 or st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1 :
      htmltext = "7608-06.htm"
   elif npcId == 7747 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_FAIRY_STONE_ID)==1 :
      htmltext = "7747-02.htm"
   elif npcId == 7747 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==1 :
      htmltext = "7747-04.htm"
   elif npcId == 7747 and int(st.get("cond"))==5 :
      htmltext = "7747-07.htm"
   elif npcId == 7747 and int(st.get("cond"))==6 and st.getQuestItemsCount(Q_JUICE_OF_MONKSHOOD_ID)==1 :
      htmltext = "7747-09.htm"
   elif npcId == 7747 and int(st.get("cond"))==7 :
     if st.getQuestItemsCount(Q_EGG_OF_DRAKE_EXARION_ID) < 20 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_ZWOV_ID) < 20 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_KALIBRAN_ID) < 20 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SUZET_ID) < 20 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SHAMHAI_ID) < 20 :
       htmltext = "7747-10.htm"
     else:
       htmltext = "7747-11.htm"
   elif npcId == 7747 and int(st.get("cond"))==8 :
     htmltext = "7747-12.htm"
#   elif npcId == 7747 and int(st.get("cond"))==0 or (int(st.get("cond"))<4 or int(st.get("cond"))>8) and (st.getQuestItemsCount(Q_FAIRY_STONE_ID)==0 and st.getQuestItemsCount(Q_FAIRY_STONE_DELUX_ID)==0) :
#     n = n0+1
#     n = n0%3
#     if n0 == 0 :
#     elif n0 == 1 :
#     else:
   elif npcId == 7748 and int(st.get("cond"))==6 and st.getQuestItemsCount(Q_JUICE_OF_MONKSHOOD_ID)==1 :
       htmltext = "7748-02.htm"
   elif npcId == 7748 and int(st.get("cond"))==7 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_EXARION_ID)<20 and st.getQuestItemsCount(Q_SCALE_OF_DRAKE_EXARION_ID)==1 :
     htmltext = "7748-04.htm"
   elif npcId == 7748 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_EXARION_ID)==20 :
     st.takeItems(Q_EGG_OF_DRAKE_EXARION_ID,st.getQuestItemsCount(Q_EGG_OF_DRAKE_EXARION_ID))
     st.takeItems(Q_SCALE_OF_DRAKE_EXARION_ID,1)
     st.giveItems(Q_EGG_OF_DRAKE_EXARION_ID,1)
     st.set("cond","8")
     htmltext = "7748-05.htm"
   elif npcId == 7748 and int(st.get("cond"))==8 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_EXARION_ID)==1 :
     htmltext = "7748-06.htm"
   elif npcId == 7749 and int(st.get("cond"))==6 and st.getQuestItemsCount(Q_JUICE_OF_MONKSHOOD_ID)==1 :
     htmltext = "7749-02.htm"
   elif npcId == 7749 and int(st.get("cond"))==7 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_ZWOV_ID)<20 and st.getQuestItemsCount(Q_SCALE_OF_DRAKE_ZWOV_ID)==1 :
     htmltext = "7749-04.htm"
   elif npcId == 7749 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_ZWOV_ID)==20 :
     st.takeItems(Q_EGG_OF_DRAKE_ZWOV_ID,st.getQuestItemsCount(Q_EGG_OF_DRAKE_ZWOV_ID))
     st.takeItems(Q_SCALE_OF_DRAKE_ZWOV_ID,1)
     st.giveItems(Q_EGG_OF_DRAKE_ZWOV_ID,1)
     st.set("cond","8")
     htmltext = "7749-05.htm"
   elif npcId == 7749 and int(st.get("cond"))==8 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_ZWOV_ID)==1 :
     htmltext = "7749-06.htm"
   elif npcId == 7750 and int(st.get("cond"))==6 and st.getQuestItemsCount(Q_JUICE_OF_MONKSHOOD_ID)==1 :
     htmltext = "7750-02.htm"
   elif npcId == 7750 and int(st.get("cond"))==7 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_KALIBRAN_ID)<20 and st.getQuestItemsCount(Q_SCALE_OF_DRAKE_KALIBRAN_ID)==1 :
     htmltext = "7750-04.htm"
   elif npcId == 7750 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_KALIBRAN_ID)==20 :
     htmltext = "7750-05.htm"
   elif npcId == 7750 and int(st.get("cond"))==8 and st.getQuestItemsCount(Q_EGG_OF_DRAKE_KALIBRAN_ID)==1 :
     htmltext = "7750-07.htm"
   elif npcId == 7751 and int(st.get("cond"))==6 and st.getQuestItemsCount(Q_JUICE_OF_MONKSHOOD_ID)==1 :
     htmltext = "7751-02.htm"
   elif npcId == 7751 and int(st.get("cond"))==7 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SUZET_ID)<20 and st.getQuestItemsCount(Q_SCALE_OF_WYRM_SUZET_ID)==1 :
     htmltext = "7751-05.htm"
   elif npcId == 7751 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SUZET_ID)==20 :
     st.takeItems(Q_EGG_OF_WYRM_SUZET_ID,st.getQuestItemsCount(Q_EGG_OF_WYRM_SUZET_ID))
     st.takeItems(Q_SCALE_OF_WYRM_SUZET_ID,1)
     st.giveItems(Q_EGG_OF_WYRM_SUZET_ID,1)
     st.set("cond","8")
     htmltext = "7751-06.htm"
   elif npcId == 7751 and int(st.get("cond"))==8 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SUZET_ID)==1 :
     htmltext = "7751-07.htm"
   elif npcId == 7752 and int(st.get("cond"))==6 and st.getQuestItemsCount(Q_JUICE_OF_MONKSHOOD_ID)==1 :
     htmltext = "7752-02.htm"
   elif npcId == 7752 and int(st.get("cond"))==7 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SHAMHAI_ID)<20 and st.getQuestItemsCount(Q_SCALE_OF_WYRM_SHAMHAI_ID)==1 :
     htmltext = "7752-04.htm"
   elif npcId == 7752 and int(st.get("cond"))>=1 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SHAMHAI_ID)==20 :
     st.takeItems(Q_EGG_OF_WYRM_SHAMHAI_ID,st.getQuestItemsCount(Q_EGG_OF_WYRM_SHAMHAI_ID))
     st.takeItems(Q_SCALE_OF_WYRM_SHAMHAI_ID,1)
     st.giveItems(Q_EGG_OF_WYRM_SHAMHAI_ID,1)
     st.set("cond","8")
     htmltext = "7752-05.htm"
   elif npcId == 7752 and int(st.get("cond"))==8 and st.getQuestItemsCount(Q_EGG_OF_WYRM_SHAMHAI_ID)==1 :
     htmltext = "7752-06.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 231 :
    if int(st.get("cond")) >= 1 and (st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FS_ID) == 1 and st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID) < 10) or st.getQuestItemsCount(Q_LIST_OF_STUFF_FOR_FSD_ID) == 1 and st.getQuestItemsCount(Q_INPICIOS_BACK_SKIN_ID) < 20 :
      if st.getRandom(100) < 30 :
        st.giveItems(Q_INPICIOS_BACK_SKIN_ID,1)
   elif npcId == 580 :
     if int(st.get("cond")) >= 1 and st.getQuestItemsCount(Q_SCALE_OF_DRAKE_EXARION_ID) == 1 :
       if st.getQuestItemsCount(Q_EGG_OF_DRAKE_EXARION_ID) < 20 and st.getRandom(100) < 50 :
         st.giveItems(Q_EGG_OF_DRAKE_EXARION_ID,1)
         st.playSound("ItemSound.quest_middle")
   elif npcId == 233 :
     if int(st.get("cond")) >= 1:
       st.giveItems(Q_SCALE_OF_DRAKE_ZWOV_ID,1)
       st.giveItems(Q_EGG_OF_DRAKE_ZWOV_ID,1)
   elif npcId == 551 :
     if int(st.get("cond")) >= 1 and st.getQuestItemsCount(Q_SCALE_OF_DRAKE_KALIBRAN_ID) == 1 :
       if st.getQuestItemsCount(Q_EGG_OF_DRAKE_KALIBRAN_ID) < 20 and st.getRandom(100) < 50 :
         st.giveItems(Q_EGG_OF_DRAKE_KALIBRAN_ID,1)
         st.playSound("ItemSound.quest_middle")
   elif npcId == 270 :
     if int(st.get("cond")) >= 1 and st.getQuestItemsCount(Q_SCALE_OF_WYRM_SUZET_ID) == 1 :
       if st.getQuestItemsCount(Q_EGG_OF_WYRM_SUZET_ID) < 20 and st.getRandom(100) < 50 :
         st.giveItems(Q_EGG_OF_WYRM_SUZET_ID,1)
         st.playSound("ItemSound.quest_middle")
   elif npcId == 202 :
     if int(st.get("cond")) >= 1:
       st.giveItems(Q_EGG_OF_WYRM_SHAMHAI_ID,1)
       st.giveItems(Q_SCALE_OF_WYRM_SHAMHAI_ID,1)
#   elif npcId == 589 or npcId == 590 or npcId == 591 or npcId == 592 or npcId == 593 or npcId == 594 or npcId == 595 or npcId == 596 or npcId == 597 or npcId == 598 or npcId == 599:
#     if int(st.get("cond")) >= 1 :
#       st.giveItems(,1)
   return

QUEST       = Quest(420,"420_LittleWings","Little Wings")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7829)
QUEST.addStartNpc(7747)

STARTED.addTalkId(7608)
STARTED.addTalkId(7610)
STARTED.addTalkId(7711)
STARTED.addTalkId(7747)
STARTED.addTalkId(7748)
STARTED.addTalkId(7749)
STARTED.addTalkId(7750)
STARTED.addTalkId(7751)
STARTED.addTalkId(7752)
STARTED.addTalkId(7829)

STARTED.addKillId(202)
STARTED.addKillId(231)
STARTED.addKillId(233)
STARTED.addKillId(270)
STARTED.addKillId(551)
STARTED.addKillId(580)
STARTED.addKillId(589)
STARTED.addKillId(590)
STARTED.addKillId(591)
STARTED.addKillId(592)
STARTED.addKillId(593)
STARTED.addKillId(594)
STARTED.addKillId(595)
STARTED.addKillId(596)
STARTED.addKillId(597)
STARTED.addKillId(598)
STARTED.addKillId(599)

STARTED.addQuestDrop(7610,Q_LIST_OF_STUFF_FOR_FS_ID,1)
STARTED.addQuestDrop(7610,Q_LIST_OF_STUFF_FOR_FSD_ID,1)
STARTED.addQuestDrop(231,Q_INPICIOS_BACK_SKIN_ID,1)
STARTED.addQuestDrop(7610,Q_LIST_OF_STUFF_FOR_FSD_ID,1)
STARTED.addQuestDrop(7608,Q_FAIRY_STONE_ID,1)
STARTED.addQuestDrop(7608,Q_FAIRY_STONE_DELUX_ID,1)
STARTED.addQuestDrop(7608,Q_FAIRY_STONE_DELUX_ID,1)
STARTED.addQuestDrop(7747,Q_FAIRY_DUST_ID,1)
STARTED.addQuestDrop(7748,Q_EGG_OF_DRAKE_EXARION_ID,1)
STARTED.addQuestDrop(580,Q_EGG_OF_DRAKE_EXARION_ID,1)
STARTED.addQuestDrop(7748,Q_SCALE_OF_DRAKE_EXARION_ID,1)
STARTED.addQuestDrop(7747,Q_JUICE_OF_MONKSHOOD_ID,1)
STARTED.addQuestDrop(7749,Q_EGG_OF_DRAKE_ZWOV_ID,1)
STARTED.addQuestDrop(7749,Q_SCALE_OF_DRAKE_ZWOV_ID,1)
STARTED.addQuestDrop(233,Q_SCALE_OF_DRAKE_ZWOV_ID,1)
STARTED.addQuestDrop(7750,Q_EGG_OF_DRAKE_KALIBRAN_ID,1)
STARTED.addQuestDrop(551,Q_EGG_OF_DRAKE_KALIBRAN_ID,1)
STARTED.addQuestDrop(7750,Q_SCALE_OF_DRAKE_KALIBRAN_ID,1)
STARTED.addQuestDrop(7751,Q_EGG_OF_WYRM_SUZET_ID,1)
STARTED.addQuestDrop(270,Q_EGG_OF_WYRM_SUZET_ID,1)
STARTED.addQuestDrop(7751,Q_SCALE_OF_WYRM_SUZET_ID,1)
STARTED.addQuestDrop(7752,Q_EGG_OF_WYRM_SHAMHAI_ID,1)
STARTED.addQuestDrop(7752,Q_SCALE_OF_WYRM_SHAMHAI_ID,1)
STARTED.addQuestDrop(202,Q_SCALE_OF_WYRM_SHAMHAI_ID,1)
