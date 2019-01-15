insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (1,0,0,0,0,0,"KamiKaze");
insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (2,0,0,0,0,0,"FightClub");
insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (3,0,0,0,0,0,"BadCompany");
insert into constant_party(cp_Id,number_of_active_players,number_of_boxes,orfen_points,core_points,aq_points,cp_Name) values (4,0,0,0,0,0,"ParaBellum");
insert into clan(clan_id, name) values (1,"Perkunas");
insert into user(user_id, email, password, type_of_user, cp_id)  values (1,"e@e.e","asd",2,1);
insert into `character`(character_id, class_of_character, in_game_name, level, clan_id, user_id) VALUES (1,0, "DrEnigma", 77, 1, 1)