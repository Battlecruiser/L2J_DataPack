# Made by Fulminus, version 0.1

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "234_FatesWhisper"

PIPETTE_KNIFE = 4665
REIRIAS_SOUL_ORB = 4666
KERMONS_INFERNIUM_SCEPTER = 4667
GOLCONDAS_INFERNIUM_SCEPTER = 4668
HALLATES_INFERNIUM_SCEPTER = 4669
REORINS_HAMMER = 4670
REORINS_MOLD = 4671
INFERNIUM_VARNISH = 4672
RED_PIPETTE_KNIFE = 4673
STAR_OF_DESTINY = 5011
CRYSTAL_B = 1460
BLOOD_STAINED_CLOTH = 14361
WHITE_CLOTH = 14362

#Reorin, Cliff, Ferris, Zenkin, Kaspar, Kernon's Chest, Golkonda's Chest, Hallate's Chest, Cabrio's "Coffer of the Dead"
NPC=[31002,30182,30847,30178,30833,31028,31029,31030,31027]

CHEST_SPAWNS = {
  25035:31027, # Shilen's Messenger Cabrio
  25054:31028, # Demon Kernon
  25126:31029, # Golkonda, the Longhorn General
  25220:31030  # Death Lord Hallate
  }

#mobId=[cond,dropId,rate]
DROPLIST={
20823: [8,BLOOD_STAINED_CLOTH,80],
20826: [8,BLOOD_STAINED_CLOTH,80],
20827: [8,BLOOD_STAINED_CLOTH,80],
20828: [8,BLOOD_STAINED_CLOTH,80],
20829: [8,BLOOD_STAINED_CLOTH,80],
21064: [8,BLOOD_STAINED_CLOTH,80],
21065: [8,BLOOD_STAINED_CLOTH,80],
21066: [8,BLOOD_STAINED_CLOTH,80],
21069: [8,BLOOD_STAINED_CLOTH,80],
21072: [8,BLOOD_STAINED_CLOTH,80],
}

Weapons={
79:"Sword of Damascus",
2626:"Samurai Dualsword",
287:"Bow of Peril",
97:"Lance",
175:"Art of Battle Axe",
210:"Staff of Evil Spirits",
234:"Demon Dagger" ,
268:"Bellion Cestus" ,
171:"Deadman's Glory" ,
7883:"Guardian Sword",
7889:"Wizard's Tear",
7901:"Star Buster",
7893:"Kaim Vanul's Bones" 
}

class Quest (JQuest) :

  def __init__(self,id,name,descr):
    JQuest.__init__(self,id,name,descr)
    self.questItemIds = [BLOOD_STAINED_CLOTH,WHITE_CLOTH,PIPETTE_KNIFE,RED_PIPETTE_KNIFE]

  def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    #accept quest
    if event == "1" :
      st.set("cond","1")
      st.setState(State.STARTED)
      htmltext = "31002-03.htm"
    # talking with cliff...last dialog to get the Infernium Varnish
    elif event == "30182_01" :
      htmltext = "30182-01c.htm"
      st.giveItems(INFERNIUM_VARNISH,1)
    # open Kernon's Chest
    elif event == "31028_01" :
      htmltext = "31028-02.htm"
      st.giveItems(KERMONS_INFERNIUM_SCEPTER,1)
    # open Hallate's Chest
    elif event == "31029_01" :
      htmltext = "31029-02.htm"
      st.giveItems(GOLCONDAS_INFERNIUM_SCEPTER,1)
    # open Golkonda's Chest
    elif event == "31030_01" :
      htmltext = "31030-02.htm"
      st.giveItems(HALLATES_INFERNIUM_SCEPTER,1)
    # dialog with Zenkin
    elif event == "30178_01" :
      st.set("cond","6")
      htmltext = "30178-01a.htm"
    # dialog with Kaspar - go to baium
    elif event == "30833_01a" :
      htmltext = "30833-01b.htm"
      st.giveItems(PIPETTE_KNIFE,1)
      st.set("cond","7")
    # dialog with Kaspar - go to toi
    elif event == "30833_01a2" :
      htmltext = "30833-01b2.htm"
      st.giveItems(WHITE_CLOTH,30)
      st.set("cond","8")
    ## FINAL ITEM EXCHANGE SECTION
    elif event.startswith("selectBGrade_"):
      if st.getInt("bypass") :
          return
      bGradeId = event.replace("selectBGrade_", "")
      st.set("weaponId",bGradeId)
      htmltext = st.showHtmlFile("31002-13.htm").replace("%weaponname%",Weapons[int(bGradeId)])
    elif event.startswith("confirmWeapon"):
        st.set("bypass","1")
        htmltext = st.showHtmlFile("31002-14.htm").replace("%weaponname%",Weapons[st.getInt("weaponId")])
    elif event.startswith("selectAGrade_"):
      if st.getInt("bypass"):
        if st.getQuestItemsCount(st.getInt("weaponId")) > 0 :  
          aGradeItemId = int(event.replace("selectAGrade_", ""))
          htmltext = "31002-12.htm"
          st.takeItems(st.getInt("weaponId"),1)
          st.giveItems(aGradeItemId,1)
          st.giveItems(STAR_OF_DESTINY,1)
          st.exitQuest(False)
          st.unset("cond")
          st.unset("bypass")
          st.unset("weaponId")
        else:
          htmltext = st.showHtmlFile("31002-15.htm").replace("%weaponname%",Weapons[st.getInt("weaponId")])
      else:
        htmltext="<html><body>Maestro Reorin:<br>Are you trying to cheat me?!  What happenned to the weapon you were about to give me for the neutralization of Infernum's evil aura?</body></html>"
        #st.exitQuest(1)
    return htmltext

  def onTalk (self,npc,player):
    htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
    st = player.getQuestState(qn)
    if not st : return htmltext

    npcId = npc.getNpcId()
    id = st.getState()

    # first time when a player join the quest
    if id == State.CREATED:
      if player.getLevel() >= 75:
        htmltext = "31002-02.htm"
      else:
        htmltext = "31002-01.htm"
        st.exitQuest(1)
      return htmltext
    # if quest is already State.COMPLETED
    elif id == State.COMPLETED:
      return "<html><body>This quest has already been completed.</body></html>"

    # if quest is accepted and in progress
    elif id == State.STARTED:
      cond =st.getInt("cond")
      if npcId == NPC[0] :
        if cond == 1 and not st.getQuestItemsCount(REIRIAS_SOUL_ORB) :  # waiting for the orb
          htmltext = "31002-04b.htm"
        elif cond == 1 :  #got the orb!  Go to the next step (infernium scepter pieces)
          st.takeItems(REIRIAS_SOUL_ORB,1)
          htmltext = "31002-05.htm"
          st.set("cond","2")
        # waiting for infernium scepter pieces
        elif cond == 2 and (st.getQuestItemsCount(KERMONS_INFERNIUM_SCEPTER)+st.getQuestItemsCount(GOLCONDAS_INFERNIUM_SCEPTER)+st.getQuestItemsCount(HALLATES_INFERNIUM_SCEPTER) < 3) :
          htmltext = "31002-05c.htm"
        elif cond == 2 :  #got the infernium scepter pieces!  Go to the next step (infernium Varnish)
          st.takeItems(KERMONS_INFERNIUM_SCEPTER,1)
          st.takeItems(GOLCONDAS_INFERNIUM_SCEPTER,1)
          st.takeItems(HALLATES_INFERNIUM_SCEPTER,1)
          htmltext = "31002-06.htm"
          st.set("cond","3")
        # waiting for infernium varnish
        elif cond == 3 and not st.getQuestItemsCount(INFERNIUM_VARNISH) :
          htmltext = "31002-06b.htm"
        elif cond == 3 :  #got the infernium varnish!  Go to the next step (Reorin's Hammer)
          st.takeItems(INFERNIUM_VARNISH,1)
          htmltext = "31002-07.htm"
          st.set("cond","4")
        # waiting for Reorin's Hammer
        elif cond == 4 and not st.getQuestItemsCount(REORINS_HAMMER) :
          htmltext = "31002-07b.htm"
        elif cond == 4 :  # got Reorin's Hammer!  Go to the next step (Reorin's Mold)
          st.takeItems(REORINS_HAMMER,1)
          htmltext = "31002-08.htm"
          st.set("cond","5")
        elif cond < 10 :     # waiting for Reorin's Mold
          htmltext = "31002-08b.htm"
        elif cond == 10 :  # got Reorin's Mold!  Go to the next step (B Crystals)
          st.takeItems(REORINS_MOLD,1)
          htmltext = "31002-09.htm"
          st.set("cond","11")
          qs = st.getPlayer().getQuestState("255_Tutorial")
          if qs:
             st.showQuestionMark(13)
             st.playSound("ItemSound.quest_tutorial")
        # waiting for 984 B Grade Crystals
        elif cond == 11 and (st.getQuestItemsCount(CRYSTAL_B) < 984) :
          htmltext = "31002-09a.htm"
        elif cond == 11 : # got the crystals
          st.takeItems(CRYSTAL_B,984)
          htmltext = "31002-BGradeList.htm"
          st.set("cond","12")
        # all is ready.  Now give a menu to trade the B weapon for the player's choice of A Weapon.
        elif cond == 12: 
          if st.getInt("bypass"):  #weapon is set
            if st.getQuestItemsCount(st.getInt("weaponId")) > 0 : #have weapon
              htmltext = st.showHtmlFile("31002-AGradeList.htm").replace("%weaponname%",Weapons[st.getInt("weaponId")])
            else: #dont have
              htmltext = st.showHtmlFile("31002-15.htm").replace("%weaponname%",Weapons[st.getInt("weaponId")])
          else :  #weapon not yet chosen
            htmltext = "31002-BGradeList.htm"
      ## CLIFF.
      # came to take the varnish
      elif npcId == NPC[1] and cond==3 and not st.getQuestItemsCount(INFERNIUM_VARNISH) :
        htmltext = "30182-01.htm"
      # you already got the varnish...why are you back?
      elif npcId == NPC[1] and (cond>=3 or st.getQuestItemsCount(INFERNIUM_VARNISH)) :
        htmltext = "30182-02.htm"
      ## FERRIS
      # go to take the mold      
      elif npcId == NPC[2] and cond==4 and not st.getQuestItemsCount(REORINS_HAMMER) :
        htmltext = "30847-01.htm"  # go to trader Zenkin
        st.giveItems(REORINS_HAMMER,1)
      # I already told you I don't have it!
      elif npcId == NPC[2] and cond>=4 :
        htmltext = "30847-02.htm"  # go to trader Zenkin
      ## ZENKIN
      # go to take mold
      elif npcId == NPC[3] and cond==5 :
        htmltext = "30178-01.htm"  # go to Magister Kaspar
      # I already told you I don't have it!  
      elif npcId == NPC[3] and cond>5 :
        htmltext = "30178-02.htm"  # go to Magister Kaspar
      ## KASPAR
      elif npcId == NPC[4]:
        # first visit: You have neither plain nor blooded knife.
        if cond==6 :
          htmltext = "30833-01.htm"  # go to Magister Hanellin,etc. Get Baium's Blood with the pipette
        #revisit after you've gotten the mold: What are you still doing here?
        if cond > 9 :
          htmltext = "30833-04.htm"  # Have you given the mold to Reorin, yet?
        # revisit before getting the blood: remind "go get the blood"
        if cond==7 and st.getQuestItemsCount(PIPETTE_KNIFE) and not st.getQuestItemsCount(RED_PIPETTE_KNIFE) :
          htmltext = "30833-02.htm"  # go to Magister Hanellin,etc. Get Baium's Blood with the pipette
        # got the blood and I'm ready to proceed
        if cond==7 and not st.getQuestItemsCount(PIPETTE_KNIFE) and st.getQuestItemsCount(RED_PIPETTE_KNIFE) :
          htmltext = "30833-03.htm"  # great! Here is your mold for Reorin
          st.takeItems(RED_PIPETTE_KNIFE,1)
          st.giveItems(REORINS_MOLD,1)
          st.set("cond","10")
        # get 30 blood cloths
        if cond==8:
          htmltext = "30833-02b.htm"
        # finished hunt 30 blood cloths, give reorin mold
        if cond==9:
          htmltext = "30833-03b.htm"
          st.takeItems(BLOOD_STAINED_CLOTH,-1)
          st.giveItems(REORINS_MOLD,1)
          st.set("cond","10")
      ## CHESTS FROM RAIDBOSSES
      elif cond==1 :
        if npcId ==NPC[8] and st.getQuestItemsCount(REIRIAS_SOUL_ORB)==0 :
          htmltext = "31027-01.htm"
          st.giveItems(REIRIAS_SOUL_ORB,1)
          st.playSound("Itemsound.quest_itemget")
      elif cond==2 :
        # Kernon's Chest
        if npcId == NPC[5] and st.getQuestItemsCount(KERMONS_INFERNIUM_SCEPTER)==0 :
          htmltext = "31028-01.htm"
        elif npcId == NPC[5] :
          htmltext = "<html><body>This chest looks empty</body></html>"
        # Golkonda's Chest
        elif npcId == NPC[6] and st.getQuestItemsCount(GOLCONDAS_INFERNIUM_SCEPTER)==0 :
          htmltext = "31029-01.htm"
        elif npcId == NPC[6] :
          htmltext = "<html><body>This chest looks empty</body></html>"
        # Hallate's Chest 
        elif npcId == NPC[7] and st.getQuestItemsCount(HALLATES_INFERNIUM_SCEPTER)==0 :
          htmltext = "31030-01.htm"
        elif npcId == NPC[7] :
          htmltext = "<html><body>This chest looks empty</body></html>"
    return htmltext

  def onAttack (self, npc, player, damage, isPet, skill):
    st = player.getQuestState(qn)
    if not st : return 
    if st.getState() != State.STARTED : return 
    if isPet : return

    if st.getInt("cond") == 7 and npc.getNpcId() == 29020 :
      if player.getActiveWeaponItem() and player.getActiveWeaponItem().getItemId() == PIPETTE_KNIFE and st.getQuestItemsCount(RED_PIPETTE_KNIFE) == 0:
        st.giveItems(RED_PIPETTE_KNIFE,1)
        st.takeItems(PIPETTE_KNIFE,1)
        st.playSound("Itemsound.quest_itemget")
    return

  def onKill(self,npc,player,isPet):
    npcId=npc.getNpcId()
    # the chests always spawn, even if the RB is killed with nobody nearby doing the quest.
    if npcId in CHEST_SPAWNS.keys() :
      self.addSpawn(CHEST_SPAWNS[npcId], npc.getX(), npc.getY(), npc.getZ(),npc.getHeading(), True, 60000)
    else :
     value,dropId,chance = DROPLIST[npcId]
     if chance > 0 and Rnd.get(100) < chance:
      party = player.getParty()
      if party :
        PartyQuestMembers = []
        for partyMember in party.getPartyMembers().toArray() :
            if partyMember.isInsideRadius(player,1500,True,False):
              pst = partyMember.getQuestState(qn)
              if pst :
                  if pst.getInt("cond") == 8 and pst.getQuestItemsCount(dropId) < 30 :
                      PartyQuestMembers.append(pst)
        if len(PartyQuestMembers) == 0 : return
        stw = PartyQuestMembers[Rnd.get(len(PartyQuestMembers))]
        stw.giveItems(dropId,1)
        stw.takeItems(WHITE_CLOTH,1)
        stw.playSound("Itemsound.quest_itemget")
        if stw.getQuestItemsCount(dropId) >= 30:
          stw.set("cond","9")
      else:
        st = player.getQuestState(qn)
        if not st : return
        if st.getState() != State.STARTED : return
        if npcId in DROPLIST.keys() :
          if st.getInt("cond") == value:
            if value == 8 and st.getQuestItemsCount(dropId) < 30:
              st.giveItems(dropId,1)
              st.takeItems(WHITE_CLOTH,1)
              st.playSound("Itemsound.quest_itemget")
              if st.getQuestItemsCount(dropId) >= 30:
                 st.set("cond","9")
    return


QUEST    = Quest(234,qn,"Fate's Whisper")


QUEST.addStartNpc(NPC[0])

for npcId in NPC:
  QUEST.addTalkId(npcId)
 
for mobId in DROPLIST.keys() :
  QUEST.addKillId(mobId)

for mobId in CHEST_SPAWNS.keys() :
  QUEST.addKillId(mobId)

QUEST.addAttackId(29020)