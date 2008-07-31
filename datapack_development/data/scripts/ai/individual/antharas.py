import sys
import math
from net.sf.l2j                                 import L2DatabaseFactory
from net.sf.l2j.gameserver.ai                   import CtrlIntention
from net.sf.l2j.gameserver.model.quest.jython   import QuestJython as JQuest
from net.sf.l2j.gameserver.model                import L2CharPosition
from net.sf.l2j.gameserver.serverpackets        import PlaySound
from net.sf.l2j.gameserver.serverpackets        import Earthquake
from net.sf.l2j.gameserver.serverpackets        import SocialAction
from net.sf.l2j.gameserver.serverpackets        import SpecialCamera
from net.sf.l2j.util                            import Rnd
from java.lang                                  import System
from net.sf.l2j.gameserver.instancemanager      import GrandBossManager

# Boss: Antharas

ANTHARAS = 29019

#Antharas Status Tracking :
DORMANT = 0     #Antharas is spawned and no one has entered yet. Entry is unlocked
WAITING = 1     #Antharas is spawend and someone has entered, triggering a 30 minute window for additional people to enter
                #before he unleashes his attack. Entry is unlocked
FIGHTING = 2    #Antharas is engaged in battle, annihilating his foes. Entry is locked
DEAD = 3        #Antharas has been killed. Entry is locked

class Antharas(JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.lastAction = 0
        self.zone = GrandBossManager.getInstance().getZone(179700,113800,-7709)
        info = GrandBossManager.getInstance().getStatsSet(ANTHARAS)
        status = GrandBossManager.getInstance().getBossStatus(ANTHARAS)
        if status == DEAD :
            # load the unlock date and time for antharas from DB
            temp = long(info.getLong("respawn_time")) - System.currentTimeMillis()
            # if antharas is locked until a certain time, mark it so and start the unlock timer
            # the unlock time has not yet expired.  Mark Antharas as currently locked.  Setup a timer
            # to fire at the correct time (calculate the time between now and the unlock time,
            # setup a timer to fire after that many msec)
            if temp > 0 :
                self.startQuestTimer("antharas_unlock", temp, None, None)
            else :
                # the time has already expired while the server was offline. Immediately spawn antharas in his cave.
                # also, the status needs to be changed to DORMANT
                antharas = self.addSpawn(ANTHARAS,185708,114298,-8221,32768,False,0)
                GrandBossManager.getInstance().setBossStatus(ANTHARAS,DORMANT)
                antharas.broadcastPacket(Earthquake(185708,114298,-8221,20,10))
                GrandBossManager.getInstance().addBoss(antharas) 
        else :
            loc_x = info.getInteger("loc_x")
            loc_y = info.getInteger("loc_y")
            loc_z = info.getInteger("loc_z")
            heading = info.getInteger("heading")
            hp = info.getInteger("currentHP")
            mp = info.getInteger("currentMP")
            antharas = self.addSpawn(ANTHARAS,loc_x,loc_y,loc_z,heading,False,0)
            GrandBossManager.getInstance().addBoss(antharas)
            antharas.setCurrentHpMp(hp,mp)
            if status == WAITING :
                # Start timer to lock entry after 30 minutes
                self.startQuestTimer("waiting",1800000, antharas, None)
            elif status == FIGHTING :
                self.lastAction = System.currentTimeMillis()
                # Start repeating timer to check for inactivity
                self.startQuestTimer("antharas_despawn",60000, antharas, None, True)

    def onAdvEvent (self,event,npc,player):
        if npc :
            if event == "waiting" :
                npc.teleToLocation(185452,114835,-8221,0)
                npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(181911,114835,-7678,0))
                self.startQuestTimer("antharas_has_arrived",2000, npc, None, True)
                npc.broadcastPacket(PlaySound(1, "BS02_A", 1, npc.getObjectId(), 185452, 114835, -8221))
                GrandBossManager.getInstance().setBossStatus(ANTHARAS,FIGHTING)
            elif event == "camera_1" :
                self.startQuestTimer("camera_2",3000, npc, None)
                npc.broadcastPacket(SpecialCamera(npc.getObjectId(),700,13,-19,0,20000))
            elif event == "camera_2" :
                self.startQuestTimer("camera_3",10000, npc, None)
                npc.broadcastPacket(SpecialCamera(npc.getObjectId(),700,13,0,6000,20000))
            elif event == "camera_3" :
                self.startQuestTimer("camera_4",200, npc, None)
                npc.broadcastPacket(SpecialCamera(npc.getObjectId(),3700,0,-3,0,10000))
            elif event == "camera_4" :
                self.startQuestTimer("camera_5",10800, npc, None)
                npc.broadcastPacket(SpecialCamera(npc.getObjectId(),1100,0,-3,22000,30000))
            elif event == "camera_5" :
                self.startQuestTimer("antharas_despawn",60000, npc, None, True)
                npc.broadcastPacket(SpecialCamera(npc.getObjectId(),1100,0,-3,300,7000))
                self.lastAction = System.currentTimeMillis()
            elif event == "antharas_despawn" :
                temp = System.currentTimeMillis() - int(self.lastAction) 
                if temp > 900000 :
                    npc.teleToLocation(185708,114298,-8221,0)
                    GrandBossManager.getInstance().setBossStatus(ANTHARAS,DORMANT)
                    npc.setCurrentHpMp(npc.getMaxHp(),npc.getMaxMp())
                    self.zone.oustAllPlayers()
                    self.cancelQuestTimer("antharas_despawn", npc, None)
            elif event == "antharas_has_arrived" :
               dx = abs(npc.getX() - 181911)
               dy = abs(npc.getY() - 114835)
               dz = abs(npc.getZ() + 7678)
               if dx <= 50 and dy <= 50 :
                   self.startQuestTimer("camera_1",2000, npc, None)
                   npc.getSpawn().setLocx(181911)
                   npc.getSpawn().setLocy(114835)
                   npc.getSpawn().setLocz(-7678)
                   npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE)
                   self.cancelQuestTimer("antharas_has_arrived", npc, None)
               else :
                   npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(181911,114835,-7678,0))
            elif event == "spawn_cubes" :
                cube = self.addSpawn(31859,177615,114941,-7709,0,False,900000)
                radius = 1500
                for i in range(0,19,1) :
                    x = radius*math.cos(i*.331) #.331~2pi/19
                    y = radius*math.sin(i*.331)
                    self.addSpawn(31859,177615+int(x),114941+int(y),-7709,0,False,900000)
                self.cancelQuestTimer("antharas_despawn", npc, None)
                self.startQuestTimer("remove_players",900000, None, None)
        else :
            if event == "antharas_unlock" :
                antharas = self.addSpawn(ANTHARAS,185708,114298,-8221,32768,False,0)
                GrandBossManager.getInstance().addBoss(antharas)
                GrandBossManager.getInstance().setBossStatus(ANTHARAS,DORMANT)
                antharas.broadcastPacket(Earthquake(185708,114298,-8221,20,10))                
            elif event == "remove_players" :
                self.zone.oustAllPlayers()
        return

    def onAttack (self,npc,player,damage,isPet):
        self.lastAction = System.currentTimeMillis()
        if GrandBossManager.getInstance().getBossStatus(ANTHARAS) != FIGHTING :
            self.zone.oustAllPlayers()
        return

    def onKill(self,npc,player,isPet):
        npc.broadcastPacket(PlaySound(1, "BS01_D", 1, npc.getObjectId(), npc.getX(), npc.getY(), npc.getZ()))
        self.startQuestTimer("spawn_cubes", 10000, npc, None)
        GrandBossManager.getInstance().setBossStatus(ANTHARAS,DEAD)
        respawnTime = long((192 + Rnd.get(145) ) * 3600000)
        self.startQuestTimer("antharas_unlock", respawnTime, None, None)
        # also save the respawn time so that the info is maintained past reboots
        info = GrandBossManager.getInstance().getStatsSet(ANTHARAS)
        info.set("respawn_time",(long(System.currentTimeMillis()) + respawnTime))
        GrandBossManager.getInstance().setStatsSet(ANTHARAS,info)
        return

# now call the constructor (starts up the ai)
QUEST      = Antharas(-1,"antharas","ai")

QUEST.addKillId(ANTHARAS)
QUEST.addAttackId(ANTHARAS)