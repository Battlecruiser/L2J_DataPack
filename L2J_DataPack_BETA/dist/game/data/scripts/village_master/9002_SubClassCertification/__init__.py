#
# Created by Gigiikun on 2009.06.01.

import sys

from com.l2jserver.gameserver.datatables import ClassListData
from com.l2jserver.gameserver.model.base         import ClassType
from com.l2jserver.gameserver.model.base         import Race
from com.l2jserver.gameserver.model.quest        import State
from com.l2jserver.gameserver.model.quest        import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.gameserver.network            import SystemMessageId
from com.l2jserver.gameserver.network.serverpackets import SystemMessage

qn = "9002_SubClassCertification"
NPC=[30026,30031,30037,30066,30070,30109,30115,30120,30154,30174,30175,30176,30187, \
     30191,30195,30288,30289,30290,30297,30358,30373,30462,30474,30498,30499,30500, \
     30503,30504,30505,30508,30511,30512,30513,30520,30525,30565,30594,30595,30676, \
     30677,30681,30685,30687,30689,30694,30699,30704,30845,30847,30849,30854,30857, \
     30862,30865,30894,30897,30900,30905,30910,30913,31269,31272,31276,31279,31285, \
     31288,31314,31317,31321,31324,31326,31328,31331,31334,31336,31755,31958,31961, \
     31965,31968,31974,31977,31996,32092,32093,32094,32095,32096,32097,32098,32145, \
     32146,32147,32150,32153,32154,32157,32158,32160,32171,32193,32199,32202,32213, \
     32214,32221,32222,32229,32230,32233,32234]
WARRIORCLASSES=[3,88,2,89,46,48,113,114,55,117,56,118,127,131,128,129,132,133]
ROGUECLASSES=[9,92,24,102,37,109,130,134,8,93,23,101,36,108]
KNIGHTCLASSES=[5,90,6,91,20,99,33,106]
SUMMONERCLASSES=[14,96,28,104,41,111]
WIZARDCLASSES=[12,94,13,95,27,103,40,110]
HEALERCLASSES=[16,97,30,105,43,112]
ENCHANTERCLASSES=[17,98,21,100,34,107,51,115,52,116,135,136]
COMMONITEM=10280
ENHANCEDITEM=10612
CLASSITEMS={
0:10281, # Warriors
1:10282, # Knights
2:10283, # Rogues
3:10287, # Enchanters
4:10284, # Wizards
5:10286, # Summoners
6:10285  # Healers
}
TRANSFORMITEMS={
0:10289, # Warriors
1:10288, # Knights
2:10290, # Rogues
3:10293, # Enchanters
4:10292, # Wizards
5:10294, # Summoners
6:10291  # Healers
}

def getClassIndex(player):
  if player.getClassId().getId() in WARRIORCLASSES:
    return 0
  if player.getClassId().getId() in KNIGHTCLASSES:
    return 1
  if player.getClassId().getId() in ROGUECLASSES:
    return 2
  if player.getClassId().getId() in ENCHANTERCLASSES:
    return 3
  if player.getClassId().getId() in WIZARDCLASSES:
    return 4
  if player.getClassId().getId() in SUMMONERCLASSES:
    return 5
  if player.getClassId().getId() in HEALERCLASSES:
    return 6
  return -1

def getCertified(player, itemId, var):
  st = player.getQuestState(qn)
  qvar = st.getGlobalQuestVar(var)
  if qvar != "" and qvar != "0" :
    return ""
  item = player.getInventory().addItem("Quest", itemId, 1, player, player.getTarget())
  st.saveGlobalQuestVar(var,str(item.getObjectId()))
  smsg = SystemMessage.getSystemMessage(SystemMessageId.EARNED_ITEM_S1)
  smsg.addItemName(item)
  player.sendPacket(smsg)
  return

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onAdvEvent (self, event, npc, player) :
   st = player.getQuestState(qn)
   htmltext = event
   if event == "GetCertified":
     if player.isSubClassActive():
       if npc.checkVillageMaster(player.getActiveClass()):
         if player.getLevel() >= 65:
           return "CertificationList.htm"
         else:
           return "9002-08.htm"
       else:
         return "9002-04.htm"
     else:
       return "9002-03.htm"
   elif event == "Obtain65":
     html = "<html><body>Subclass Skill Certification:<br>You are trying to obtain level %level% certification of %class%, %skilltype%. Remember that once this subclass is certified, it cannot be re-certified -- even if you delete this class and develop another one -- without a special and expensive cancellation process.<br>Do you still want to be certified?<br><a action=\"bypass -h Quest 9002_SubClassCertification %event%\">Obtain certification.</a><br><a action=\"bypass -h Quest 9002_SubClassCertification 9002-05.htm\">Do not obtain certification.</a></body></html>"
     htmltext = html.replace("%level%","65").replace("%class%",str(ClassListData.getInstance().getClass(player.getActiveClass()).getClassName(1))).replace("%skilltype%","common skill").replace("%event%","lvl65Emergent")
   elif event == "Obtain70":
     html = "<html><body>Subclass Skill Certification:<br>You are trying to obtain level %level% certification of %class%, %skilltype%. Remember that once this subclass is certified, it cannot be re-certified -- even if you delete this class and develop another one -- without a special and expensive cancellation process.<br>Do you still want to be certified?<br><a action=\"bypass -h Quest 9002_SubClassCertification %event%\">Obtain certification.</a><br><a action=\"bypass -h Quest 9002_SubClassCertification 9002-05.htm\">Do not obtain certification.</a></body></html>"
     htmltext = html.replace("%level%","70").replace("%class%",str(ClassListData.getInstance().getClass(player.getActiveClass()).getClassName(1))).replace("%skilltype%","common skill").replace("%event%","lvl70Emergent")
   elif event == "Obtain75":
     html = "<html><body>Subclass Skill Certification:<br>You are trying to obtain level %level% certification of %class%, %skilltype%. Remember that once this subclass is certified, it cannot be re-certified -- even if you delete this class and develop another one -- without a special and expensive cancellation process.<br>Do you still want to be certified?<br><a action=\"bypass -h Quest 9002_SubClassCertification %event1%\">Obtain class specific skill certification.</a><br><a action=\"bypass -h Quest 9002_SubClassCertification %event2%\">Obtain master skill certification.</a><br><a action=\"bypass -h Quest 9002_SubClassCertification 9002-05.htm\">Do not obtain certification.</a></body></html>"
     htmltext = html.replace("%level%","75").replace("%class%",str(ClassListData.getInstance().getClass(player.getActiveClass()).getClassName(1))).replace("%skilltype%","common skill or special skill").replace("%event1%","lvl75Class").replace("%event2%","lvl75Master")
   elif event == "Obtain80":
     html = "<html><body>Subclass Skill Certification:<br>You are trying to obtain level %level% certification of %class%, %skilltype%. Remember that once this subclass is certified, it cannot be re-certified -- even if you delete this class and develop another one -- without a special and expensive cancellation process.<br>Do you still want to be certified?<br><a action=\"bypass -h Quest 9002_SubClassCertification %event%\">Obtain certification.</a><br><a action=\"bypass -h Quest 9002_SubClassCertification 9002-05.htm\">Do not obtain certification.</a></body></html>"
     htmltext = html.replace("%level%","80").replace("%class%",str(ClassListData.getInstance().getClass(player.getActiveClass()).getClassName(1))).replace("%skilltype%","transformation skill").replace("%event%","lvl80Class") 
   elif event.startswith("lvl"):
     level = int(event[3:5])
     type = event.replace(event[0:5],"")
     prefix = "-" + str(player.getClassIndex())
     if type == "Emergent":
       isAvailable65 = st.getGlobalQuestVar("EmergentAbility65" + prefix)
       isAvailable70 = st.getGlobalQuestVar("EmergentAbility70" + prefix)
       if event == "lvl65Emergent":
         if isAvailable65 == "" or isAvailable65 == "0":
           if player.getLevel() > 64:
             itemId = COMMONITEM
             var = "EmergentAbility" + str(level) + prefix
             getCertified(player, itemId, var)
             return "9002-07.htm"
           else:
             html = "<html><body>Subclass Skill Certification:<br>You are not yet ready to receive your level %level% certification. Work hard and come back later.</body></html>"
             htmltext = html.replace("%level%",(event[3:5]))
             return htmltext
         else:
           return "9002-06.htm"
       elif event == "lvl70Emergent":
         if isAvailable70 == "" or isAvailable70 == "0":
           if player.getLevel() > 69:
             itemId = COMMONITEM
             var = "EmergentAbility" + str(level) + prefix
             getCertified(player, itemId, var)
             return "9002-07.htm"
           else:
             html = "<html><body>Subclass Skill Certification:<br>You are not yet ready to receive your level %level% certification. Work hard and come back later.</body></html>"
             htmltext = html.replace("%level%",(event[3:5]))
             return htmltext
         else:
           return "9002-06.htm"
     elif type == "Master":
       isAvailable = st.getGlobalQuestVar("ClassAbility75" + prefix)
       if isAvailable == "" or isAvailable == "0":
         if player.getLevel() > 74:
           itemId = ENHANCEDITEM
           var = "ClassAbility" + str(level) + prefix
           getCertified(player, itemId, var)
           return "9002-07.htm"
         else:
           html = "<html><body>Subclass Skill Certification:<br>You are not yet ready to receive your level %level% certification. Work hard and come back later.</body></html>"
           htmltext = html.replace("%level%",(event[3:5]))
           return htmltext
       else:
         return "9002-06.htm"
     elif type == "Class": 
       if level == 75:
         isAvailable = st.getGlobalQuestVar("ClassAbility75" + prefix)
         if isAvailable == "" or isAvailable == "0":
           if player.getLevel() > 74:
             itemId = CLASSITEMS[getClassIndex(player)]
             var = "ClassAbility" + str(level) + prefix
             getCertified(player, itemId, var)
             return "9002-07.htm"
           else:
             html = "<html><body>Subclass Skill Certification:<br>You are not yet ready to receive your level %level% certification. Work hard and come back later.</body></html>"
             htmltext = html.replace("%level%",(event[3:5]))
             return htmltext
         else:
           return "9002-06.htm"
       elif level == 80:
         isAvailable = st.getGlobalQuestVar("ClassAbility80" + prefix)
         if isAvailable == "" or isAvailable == "0":
           if player.getLevel() > 79:  
             itemId = TRANSFORMITEMS[getClassIndex(player)]
             var = "ClassAbility" + str(level) + prefix
             getCertified(player, itemId, var)
             return "9002-07.htm"
           else:
             html = "<html><body>Subclass Skill Certification:<br>You are not yet ready to receive your level %level% certification. Work hard and come back later.</body></html>"
             htmltext = html.replace("%level%",(event[3:5]))
             return htmltext
         else:
           return "9002-06.htm"
   return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   if npcId in NPC:
     st.set("cond","0")
     st.setState(State.STARTED)
     return "9002-01.htm"

QUEST       = Quest(-1,qn,"village_master")

for item in NPC:
   QUEST.addStartNpc(item)
   QUEST.addTalkId(item)