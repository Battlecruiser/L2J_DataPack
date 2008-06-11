update clanhall set lease=0 where Grade=0;
update clanhall set lease=200000 where Grade=1;
update clanhall set lease=500000 where Grade=2;
update clanhall set lease=1000000 where Grade=3;