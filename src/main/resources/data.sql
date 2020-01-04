
INSERT INTO PLAYERS (  PLAYER_ID,   NAME	,  EMIL, PHONE_NUMBER ,USER_ID ,PASSWORD) VALUES
(1, 'Bill Gillund', 'bill.gillund@gmail.com','708-666-7777','gillundb','addison1g'),
(2, 'Mike Gillund', 'mgillund@gillundsales.com','708-666-7777','gillundm','donna'),
(3, 'Dan Hathaway', 'hathaway@gmail.com','708-666-1212','hathawayd','candice'),
(4, 'Rich Graham', 'rgraham@gmail.com','708-666-3434','grahamr','deb'),
(5, 'Mark Woyna', 'mwayna@cboe.com','708-666-5656','woynam','single');

INSERT INTO COURSES  ( COURSE_ID, COURSE_NAME, SLOPE,RATING,YARDAGE,COMMENT) VALUES
(1, 'IdleWild Country Club',136, 72.1,6400, 'My Home Course'),
(2, 'LostMarsh',132, 72.1,6800, 'first 5 dangerous'),
(3, 'Sanctuary',128, 72.1,6200, 'close to house'),
(4, 'HarborSide golf club ',131, 72.1,6400, 'can lose alot of balls'),
(5, 'Dubs dread ',137, 72.1,6400, 'super hard course'),
(6, 'GlenWoodie',131, 72.1,6500, 'My first course'),
(7, 'Flossmore country club',136, 72.1,6400, 'top 50 in illinois');
 
INSERT INTO ROUND (ROUND_ID, PLAYER_ID,ROUND_DATE, NAME,COURSE,SCORE, SLOPE , RATING, DELTA, INUSE ) VALUES
(1,1,'2019-10-25 10:00:00', 'Bill Gillund', 'Idlewild Country Club',72,136,72.1,3,0),
(2,1,'2019-10-26 10:00:00', 'Bill Gillund', 'Idlewild Country Club',73,136,72.1,3,0),
(3,1,'2019-10-27 10:00:00', 'Bill Gillund', 'Idlewild Country Club',74,136,72.1,3,0),
(4,1,'2019-10-28 10:00:00', 'Bill Gillund', 'Idlewild Country Club',75,136,72.1,3,0),
(5,1,'2019-10-29 10:00:00', 'Bill Gillund', 'Idlewild Country Club',76,136,72.1,3,0),
(6,1,'2019-10-30 10:00:00', 'Bill Gillund', 'Idlewild Country Club',77,136,72.1,3,0),
(7,1,'2019-10-2 10:00:00', 'Bill Gillund', 'Idlewild Country Club',78,136,72.1,3,0),
(8,1,'2019-10-3 10:00:00', 'Bill Gillund', 'Idlewild Country Club',79,136,72.1,3,0),
(9,1,'2019-10-4 10:00:00', 'Bill Gillund', 'Idlewild Country Club',80,136,72.1,3,0),
(10,1,'2019-10-5 10:00:00', 'Bill Gillund', 'Idlewild Country Club',81,136,72.1,3,0),
(11,1,'2019-10-6 10:00:00', 'Bill Gillund', 'Idlewild Country Club',82,136,72.1,3,0);
