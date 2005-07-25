\ Change name of current player
: player_rename ( "new_name" player -- )

    >R \ идентификатор игрока сохраняем в стек возврата. Будет считывать по R@

	\ Формируем MySQL запрос
    "UPDATE `characters` SET `char_name` = '" 
    over ( new_name update new_name )  >slashes s+
    "' WHERE `obj_Id` = " s+
    r@ "ObjectId" p@ >s s+
    
	\ Сохраним в стеке возвратов текущее значение warning и выключим предупреждения
    warning @ >r warning off
    
    update \ выполняем SQL запрос

    r> warning ! \ восстанавливаем значение переменной warning

    if ( если апдейт неудачен )
        "Can't change name to " swap s+ dup show .
        1 \ Возвращаем состояние ошибки
    else ( всё ок! )
        drop
		r@ remove-from-all-players \ удаляем из списка всех игроков, чтобы не множился
        dup r@ "Name" p! \ Устанавливаем новое значение .Name класса L2PcInstance
        r@ dup coords@ teleport_player_to \ Телепортируем игрока в его же координаты - чтобы обновилось имя
        "Successfull changed name to " swap s+ dup show .
        0 \ ok
    then
    rdrop \ сбрасываем из стека возврата ненужный уже идентификатор
;

: rename_for_price ( price "new_name" -- )
	\ Проверяем, есть ли у нас нужная сумма
    over player@ adena@ > if 
        drop 
        "Not enough adena" show 
        exit 
    then
    
	\ Переименовываем
    player@ player_rename if ( переименование неудачно )
        drop 
        exit
    then

	\ Отнимаем адену
    player@ adena-!
;

: rename: ( price -- / new name to end of string )
	tail rename_for_price
;
