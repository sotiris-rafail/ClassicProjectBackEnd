insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (1,0,0,0,0,0,"KamiKaze");
insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (2,0,0,0,0,0,"FightClub");
insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (3,0,0,0,0,0,"BadCompany");
insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (4,0,0,0,0,0,"ParaBellum");
insert into clan(clan_id, name) values (1,"Perkunas1");
insert into clan(clan_id, name) values (2,"Perkunas2");
insert into clan(clan_id, name) values (3,"Perkunas3");
insert into user(email, password, type_of_user, cp_id)  values ("e@e1.e","asd",0,1);
insert into user(email, password, type_of_user)  values ("e@e2.e","asd",1);
insert into user(email, password, type_of_user)  values ("e@e3.e","asd",1);
insert into user(email, password, type_of_user)  values ("e@e4.e","asd",2);
insert into user(email, password, type_of_user)  values ("e@e5.e","asd",2);
insert into user(email, password, type_of_user)  values ("e@e6.e","asd",2);
insert into user(email, password, type_of_user)  values ("e@e7.e","asd",1);
insert into user(email, password, type_of_user)  values ("e@e8.e","asd",1);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id, type_of_character) VALUES (1,0, "Char1", 77, 1, 1, 0);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id, type_of_character) VALUES (2,1, "Char2", 76, 1, 2, 0);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id, type_of_character) VALUES (3,2, "Char3", 77, 1, 3, 0);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id, type_of_character) VALUES (4,3, "Char4", 77, 1, 4, 0);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id, type_of_character) VALUES (5,4, "Char5", 77, 1, 5, 0);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id, type_of_character) VALUES (6,5, "Char6", 77, 1, 6, 0);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id, type_of_character) VALUES (7,6, "Char7", 77, 1, 7, 0);
insert into raid_boss(epic_boss_points, level, name, time_of_death, type_of_raid_boss, where_it_lives, window_starts, window_ends)
VALUES (3,52,'Orfen','2019-02-05 12:13:45',0,'Sea Of Spores', '01:00', '01:12');
insert into raid_boss(epic_boss_points, level, name, time_of_death, type_of_raid_boss, where_it_lives, window_starts, window_ends)
VALUES (1,40,'Queen Ant','2019-02-05 12:13:45',0,'Ant Nest', '01:00', '01:00');
insert into raid_boss(epic_boss_points, level, name, time_of_death, type_of_raid_boss, where_it_lives, window_starts, window_ends)
VALUES (3,52,'Core','2019-02-05 12:13:45',0,'Cruma Tower', '01:00', '1:12');
insert into raid_boss(epic_boss_points, level, name, time_of_death, type_of_raid_boss, where_it_lives, window_starts, window_ends)
VALUES (0,75,'Decar','2019-02-05 12:13:45',2,'Cruma Tower', '01:06', '01:06');
insert into raid_boss(epic_boss_points, level, name, time_of_death, type_of_raid_boss, where_it_lives, window_starts, window_ends)
VALUES (0,75,'Ipos','2019-02-05 12:13:45',2,'Plains of Lizardman','01:06', '01:06');
insert into raid_boss(epic_boss_points, level, name, time_of_death, type_of_raid_boss, where_it_lives, window_starts, window_ends)
VALUES (0,75,'Angel','2019-02-05 12:13:45',2,'Giran Territory','01:06', '01:06');