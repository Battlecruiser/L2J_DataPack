# Made by Kerberos v1.0 on 2008/08/01
# this script is part of the Official L2J Datapack Project.
# Visit http://forum.l2jdp.com for more details.

import sys
from net.sf.l2j.gameserver import GameTimeController
from net.sf.l2j.gameserver.ai import CtrlIntention
from net.sf.l2j.gameserver.model import L2CharPosition
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.network.serverpackets import NpcSay
from net.sf.l2j.gameserver.network.serverpackets import PlaySound
from net.sf.l2j.gameserver.network.serverpackets import SocialAction

TEXT = ["How come people are not here... We are about to start the show.. Hmm", \
        "Ugh, I have butterflies in my stomach.. The show starts soon...", \
        "Thank you all for comming here tonight.", \
        "It is an honor to have the special show today.", \
        "Our Fantasy Isle is fully committed to your happiness.", \
        "Now I'd like to introduce the most beautiful singer in Aden. Please welcome Leyla Mira!", \
        "Here she comes!","Thank you very much, Leyla. Next is", \
        "It was very difficult to invite this first group that just came back from their world tour. Let's welcome the Fantasy Isle Circus!", \
        "Come on ~ everyone","Did you like it? That was so amazing.", \
        "Now we also invited individuals with special talents.","Let's welcome the first person here!", \
        ";;;;;;Oh","Okay, now here comes the next person. Come on up please.", \
        "Oh, it looks like something great is going to happen, right?", "Oh, my ;;;;", \
        "That's g- .. great. Now, here comes the last person.","Now this is the end of today's show.", \
        "How was it? I am not sure if you really enjoyned it.", \
        "Please remember that Fantasy Isle is always planning a lot of great shows for you.", \
        "Well, I wish I could continue all night long, but this is it for today. Thank you."]

#event : [text,next event,time to next event]
TALKS = { 
"1"  : [TEXT[1],"2",1000],
"2"  : [TEXT[2],"3",6000],
"3"  : [TEXT[3],"4",4000],
"4"  : [TEXT[4],"5",5000],
"5"  : [TEXT[5],"6",3000],
"8"  : [TEXT[8],"9",5000],
"9"  : [TEXT[9],"10",5000],
"12" : [TEXT[11],"13",5000],
"13" : [TEXT[12],"14",5000],
"15" : [TEXT[13],"16",5000],
"16" : [TEXT[14],"17",5000],
"18" : [TEXT[16],"19",5000],
"19" : [TEXT[17],"20",5000],
"21" : [TEXT[18],"22",5000],
"22" : [TEXT[19],"23",400],
"25" : [TEXT[20],"26",5000],
"26" : [TEXT[21],"27",5400]
}

#event : [x,y,z,next event,time to next event]
WALKS  = {
"npc1_1" : [-56546,-56384,-2008,"npc1_2",1200],
"npc1_2" : [-56597,-56384,-2008,"npc1_3",1200],
"npc1_3" : [-56596,-56428,-2008,"npc1_4",1200],
"npc1_4" : [-56593,-56474,-2008,"npc1_5",1000],
"npc1_5" : [-56542,-56474,-2008,"npc1_6",1000],
"npc1_6" : [-56493,-56473,-2008,"npc1_7",2000],
"npc1_7" : [-56495,-56425,-2008,"npc1_1",4000],
"npc2_1" : [-56550,-56291,-2008,"npc2_2",1200],
"npc2_2" : [-56601,-56293,-2008,"npc2_3",1200],
"npc2_3" : [-56603,-56247,-2008,"npc2_4",1200],
"npc2_4" : [-56605,-56203,-2008,"npc2_5",1000],
"npc2_5" : [-56553,-56202,-2008,"npc2_6",1100],
"npc2_6" : [-56504,-56200,-2008,"npc2_7",2000],
"npc2_7" : [-56503,-56243,-2008,"npc2_1",4000],
"npc3_1" : [-56500,-56290,-2008,"npc3_2",1200],
"npc3_2" : [-56551,-56313,-2008,"npc3_3",1200],
"npc3_3" : [-56601,-56293,-2008,"npc3_4",1200],
"npc3_4" : [-56651,-56294,-2008,"npc3_5",1200],
"npc3_5" : [-56653,-56250,-2008,"npc3_6",1200],
"npc3_6" : [-56654,-56204,-2008,"npc3_7",1200],
"npc3_7" : [-56605,-56203,-2008,"npc3_8",1200],
"npc3_8" : [-56554,-56202,-2008,"npc3_9",1200],
"npc3_9" : [-56503,-56200,-2008,"npc3_10",1200],
"npc3_10": [-56502,-56244,-2008,"npc3_1",900],
"npc4_1" : [-56495,-56381,-2008,"npc4_2",1200],
"npc4_2" : [-56548,-56383,-2008,"npc4_3",1200],
"npc4_3" : [-56597,-56383,-2008,"npc4_4",1200],
"npc4_4" : [-56643,-56385,-2008,"npc4_5",1200],
"npc4_5" : [-56639,-56436,-2008,"npc4_6",1200],
"npc4_6" : [-56639,-56473,-2008,"npc4_7",1200],
"npc4_7" : [-56589,-56473,-2008,"npc4_8",1200],
"npc4_8" : [-56541,-56473,-2008,"npc4_9",1200],
"npc4_9" : [-56496,-56473,-2008,"npc4_10",1200],
"npc4_10": [-56496,-56429,-2008,"npc4_1",900],
"npc5_1" : [-56549,-56335,-2008,"npc5_2",1000],
"npc5_2" : [-56599,-56337,-2008,"npc5_3",2000],
"npc5_3" : [-56649,-56341,-2008,"npc5_4",26000],
"npc5_4" : [-56600,-56341,-2008,"npc5_5",1000],
"npc5_5" : [-56553,-56341,-2008,"npc5_6",1000],
"npc5_6" : [-56508,-56331,-2008,"npc5_2",8000],
"npc6_1" : [-56595,-56428,-2008,"npc6_2",1000],
"npc6_2" : [-56596,-56383,-2008,"npc6_3",1000],
"npc6_3" : [-56648,-56384,-2008,"npc6_4",1000],
"npc6_4" : [-56645,-56429,-2008,"npc6_5",1000],
"npc6_5" : [-56644,-56475,-2008,"npc6_6",1000],
"npc6_6" : [-56595,-56473,-2008,"npc6_7",1000],
"npc6_7" : [-56542,-56473,-2008,"npc6_8",1000],
"npc6_8" : [-56492,-56472,-2008,"npc6_9",1200],
"npc6_9" : [-56495,-56426,-2008,"npc6_10",2000],
"npc6_10": [-56540,-56426,-2008,"npc6_1",3000],
"npc7_1" : [-56603,-56249,-2008,"npc7_2",1000],
"npc7_2" : [-56601,-56294,-2008,"npc7_3",1000],
"npc7_3" : [-56651,-56295,-2008,"npc7_4",1000],
"npc7_4" : [-56653,-56248,-2008,"npc7_5",1000],
"npc7_5" : [-56605,-56203,-2008,"npc7_6",1000],
"npc7_6" : [-56554,-56202,-2008,"npc7_7",1000],
"npc7_7" : [-56504,-56201,-2008,"npc7_8",1000],
"npc7_8" : [-56502,-56247,-2008,"npc7_9",1200],
"npc7_9" : [-56549,-56248,-2008,"npc7_10",2000],
"npc7_10": [-56549,-56248,-2008,"npc7_1",3000],
"npc8_1" : [-56493,-56426,-2008,"npc8_2",1000],
"npc8_2" : [-56497,-56381,-2008,"npc8_3",1200],
"npc8_3" : [-56544,-56381,-2008,"npc8_4",1200],
"npc8_4" : [-56596,-56383,-2008,"npc8_5",1200],
"npc8_5" : [-56594,-56428,-2008,"npc8_6",900],
"npc8_6" : [-56645,-56429,-2008,"npc8_7",1200],
"npc8_7" : [-56647,-56384,-2008,"npc8_8",1200],
"npc8_8" : [-56649,-56362,-2008,"npc8_9",9200],
"npc8_9" : [-56654,-56429,-2008,"npc8_10",1200],
"npc8_10": [-56644,-56474,-2008,"npc8_11",900],
"npc8_11": [-56593,-56473,-2008,"npc8_12",1100],
"npc8_12": [-56543,-56472,-2008,"npc8_13",1200],
"npc8_13": [-56491,-56471,-2008,"npc8_1",1200],
"npc9_1" : [-56505,-56246,-2008,"npc9_2",1000],
"npc9_2" : [-56504,-56291,-2008,"npc9_3",1200],
"npc9_3" : [-56550,-56291,-2008,"npc9_4",1200],
"npc9_4" : [-56600,-56292,-2008,"npc9_5",1200],
"npc9_5" : [-56603,-56248,-2008,"npc9_6",900],
"npc9_6" : [-56653,-56249,-2008,"npc9_7",1200],
"npc9_7" : [-56651,-56294,-2008,"npc9_8",1200],
"npc9_8" : [-56650,-56316,-2008,"npc9_9",9200],
"npc9_9" : [-56660,-56250,-2008,"npc9_10",1200],
"npc9_10": [-56656,-56205,-2008,"npc9_11",900],
"npc9_11": [-56606,-56204,-2008,"npc9_12",1100],
"npc9_12": [-56554,-56203,-2008,"npc9_13",1200],
"npc9_13": [-56506,-56203,-2008,"npc9_1",1200],
"24"     : [-56730,-56340,-2008,"25",1800],
"27"     : [-56702,-56340,-2008,"29",1800]
}


class MC_Show(JQuest) :
  def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.MC = 32433
     self.singers = [32431,32432]
     self.circus = [32442,32443,32444,32445,32446]
     self.individuals = [32439,32440,32441]
     self.showstuff = [32424,32425,32426,32427,32428]
     self.startQuestTimer("timer_check",60000, None, None, True)
     self.isSpawned = 0

  def AutoChat(self, npc,text,type) :
     sm = NpcSay(npc.getObjectId(), type, npc.getNpcId(), text)
     npc.broadcastPacket(sm)

  def onAdvEvent (self,event,npc,pc) :
    if event == "timer_check" :
       gameTime = GameTimeController.getInstance().getGameTime()
       h = (gameTime/60)%24
       m = gameTime%60
       if h == 20 and m >= 27 and m <= 33:
          self.startQuestTimer("Start",100, None, None)
          self.cancelQuestTimer("timer_check", None, None)
    elif event == "Start" :
       mc = self.addSpawn(self.MC,-56698,-56430,-2008,32768,False,0)
       self.AutoChat(mc,TEXT[0],1)
       self.startQuestTimer("1",30000, mc, None)
    elif event in TALKS.keys() and npc:
       text,nextEvent,time=TALKS[event]
       self.AutoChat(npc,text,1)
       self.startQuestTimer(nextEvent,time, npc, None)
    elif event in WALKS.keys() and npc and self.isSpawned == 1:
       x,y,z,nextEvent,time=WALKS[event]
       npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(x,y,z,0))
       self.startQuestTimer(nextEvent,time, npc, None)
    elif event == "6" and npc :
       self.AutoChat(npc,TEXT[6],1)
       npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56511,-56647,-2008,36863))
       npc.broadcastPacket(PlaySound(1, "NS22_F", 0, 0, 0, 0, 0))
       elf = self.addSpawn(self.singers[0],-56344,-56328,-2008,32768,False,0)
       elf.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56657,-56338,-2008,33102))
       elf1 = self.addSpawn(self.singers[1],-56552,-56245,-2008,36863,False,0)
       elf2 = self.addSpawn(self.singers[1],-56546,-56426,-2008,28672,False,0)
       elf3 = self.addSpawn(self.singers[1],-56570,-56473,-2008,28672,False,0)
       elf4 = self.addSpawn(self.singers[1],-56594,-56516,-2008,28672,False,0)
       elf5 = self.addSpawn(self.singers[1],-56580,-56203,-2008,36863,False,0)
       elf6 = self.addSpawn(self.singers[1],-56606,-56157,-2008,36863,False,0)
       for i in [elf,elf1,elf2,elf3,elf4,elf5,elf6]:
          self.startQuestTimer("social1",6000, i, None, True)
       for j in [npc,elf,elf1,elf2,elf3,elf4,elf5,elf6]:
          self.startQuestTimer("7",215000, j, None)
    elif event == "7" and npc :
       if npc.getNpcId() == self.MC:
          self.AutoChat(npc,TEXT[7],1)
          npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56698,-56430,-2008,32768))
          self.startQuestTimer("8",12000, npc, None)
       else:
          self.cancelQuestTimer("social1", npc, None)
          npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56594,-56064,-2008,32768))
          self.startQuestTimer("clean_npc",9000, npc, None)
    elif event == "10" and npc :
       npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56483,-56665,-2034,32768))
       npc1 = self.addSpawn(self.circus[0],-56495,-56375,-2008,32768,False,0)
       npc2 = self.addSpawn(self.circus[0],-56491,-56289,-2008,32768,False,0)
       npc3 = self.addSpawn(self.circus[1],-56502,-56246,-2008,32768,False,0)
       npc4 = self.addSpawn(self.circus[1],-56496,-56429,-2008,32768,False,0)
       npc5 = self.addSpawn(self.circus[2],-56505,-56334,-2008,32768,False,0)
       npc6 = self.addSpawn(self.circus[3],-56545,-56427,-2008,32768,False,0)
       npc7 = self.addSpawn(self.circus[3],-56552,-56248,-2008,32768,False,0)
       npc8 = self.addSpawn(self.circus[4],-56493,-56473,-2008,32768,False,0)
       npc9 = self.addSpawn(self.circus[4],-56504,-56201,-2008,32768,False,0)
       npc.broadcastPacket(PlaySound(1, "TP05_F", 0, 0, 0, 0, 0))
       self.startQuestTimer("npc1_1",3000, npc1, None)
       self.startQuestTimer("npc2_1",3000, npc2, None)
       self.startQuestTimer("npc3_1",3000, npc3, None)
       self.startQuestTimer("npc4_1",3000, npc4, None)
       self.startQuestTimer("npc5_1",3500, npc5, None)
       self.startQuestTimer("npc6_1",4000, npc6, None)
       self.startQuestTimer("npc7_1",4000, npc7, None)
       self.startQuestTimer("npc8_1",3000, npc8, None)
       self.startQuestTimer("npc9_1",3000, npc9, None)
       self.isSpawned = 1
       for j in [npc,npc1,npc2,npc3,npc4,npc5,npc6,npc7,npc8,npc9]:
          self.startQuestTimer("11",100000, j, None)
    elif event == "11" and npc :
       if npc.getNpcId() == self.MC:
          self.AutoChat(npc,TEXT[10],1)
          npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56698,-56430,-2008,32768))
          self.startQuestTimer("12",5000, npc, None)
       else:
          npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56343,-56330,-2008,32768))
          self.startQuestTimer("clean_npc",1000, npc, None)
    elif event == "14" and npc :
       npc1 = self.addSpawn(self.individuals[0],-56700,-56385,-2008,32768,False,0)
       self.startQuestTimer("social1",2000, npc1, None)
       self.startQuestTimer("clean_npc",49000, npc1, None)
       self.startQuestTimer("15",7000, npc, None)
    elif event == "17" and npc :
       self.AutoChat(npc,TEXT[15],1)
       npc1 = self.addSpawn(self.individuals[1],-56700,-56340,-2008,32768,False,0)
       self.startQuestTimer("social1",2000, npc1, None)
       self.startQuestTimer("clean_npc",32000, npc1, None)
       self.startQuestTimer("18",9000, npc, None)
    elif event == "20" and npc :
       npc1 = self.addSpawn(self.individuals[2],-56703,-56296,-2008,32768,False,0)
       self.startQuestTimer("social1",2000, npc1, None)
       self.startQuestTimer("clean_npc",13000, npc1, None)
       self.startQuestTimer("21",8000, npc, None)
    elif event == "23" and npc :
       npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56702,-56340,-2008,32768))
       self.startQuestTimer("24",2800, npc, None)
       npc1 = self.addSpawn(self.showstuff[0],-56672,-56406,-2000,32768,False,0)
       npc2 = self.addSpawn(self.showstuff[1],-56648,-56368,-2000,32768,False,0)
       npc3 = self.addSpawn(self.showstuff[2],-56608,-56338,-2000,32768,False,0)
       npc4 = self.addSpawn(self.showstuff[3],-56652,-56307,-2000,32768,False,0)
       npc5 = self.addSpawn(self.showstuff[4],-56672,-56272,-2000,32768,False,0)
       for j in [npc1,npc2,npc3,npc4,npc5]:
          self.startQuestTimer("social1",5500, j, None)
          self.startQuestTimer("social1_1",12500, j, None)
          self.startQuestTimer("28",19700, j, None)
    elif event == "28" and npc :
       self.AutoChat(npc,"We love you.",0)
       self.startQuestTimer("social1",1, npc, None)
       self.startQuestTimer("clean_npc",1200, npc, None)
    elif event == "29" and npc :
       npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(-56730,-56340,-2008,32768))
       self.startQuestTimer("clean_npc",4100, npc, None)
       self.startQuestTimer("timer_check",60000, None, None, True)
    elif event in ["social1","social1_1"] and npc :
       npc.broadcastPacket(SocialAction(npc.getObjectId(),1))
    elif event == "clean_npc" and npc :
       if npc.getNpcId() in [self.circus[0], self.circus[1], self.circus[2],self.circus[3],self.circus[4]] :
          self.isSpawned = 0
       npc.deleteMe()
    return 

QUEST       = MC_Show(-11, "MC_Show", "fantasy_isle")
