-- 학습 언어 값 삽입
INSERT INTO languages (language_name)
SELECT * FROM (SELECT '영국영어' AS language_name UNION ALL
                SELECT '호주영어' UNION ALL
                SELECT '일본어' UNION ALL
                SELECT '셰틀랜트어' UNION ALL
                SELECT '바스크어') AS temp
WHERE NOT EXISTS (SELECT 1 FROM languages WHERE language_name IN ('영국영어', '호주영어', '일본어', '셰틀랜트어', '바스크어'));

-- 아이템 리스트 값 삽입
INSERT INTO items (name, description, price, payment_type)
SELECT * FROM (SELECT '경험치 2배 포션 15분' AS name,
                       '아이템을 구매하면 다음 15분 동안 얻는 경험치가 2배로 증가합니다' AS description,
                       100 AS price,
                       'IN_GAME_CASH' AS payment_type
                UNION ALL
                SELECT '경험치 2배 포션 30분',
                       '아이템을 구매하면 다음 30분 동안 얻는 경험치가 2배로 증가합니다',
                       200,
                       'IN_GAME_CASH'
                UNION ALL
                SELECT '재화 2배 포션',
                       '아이템을 구매하면 다음 15분 동안 얻는 경험치가 2배로 증가합니다',
                       50,
                       'IN_GAME_CASH'
                UNION ALL
                SELECT '일일 슈퍼 도지',
                       '아이템을 구매하면 24시간 동안 슈퍼 도지 상태가 됩니다',
                       2000,
                       'IN_GAME_CASH'
                UNION ALL
                SELECT '슈퍼 도지',
                       '광고없이 서비스를 즐기실 수 있습니다',
                       5500,
                       'CASH'
                ) AS temp
WHERE NOT EXISTS (SELECT 1 FROM items WHERE name IN ('경험치 2배 포션 15분',
                                                      '경험치 2배 포션 30분',
                                                      '재화 2배 포션',
                                                      '일일 슈퍼 도지',
                                                      '슈퍼 도지'));

---- TEXT_SELECT (medium) 문제 insert
--insert into questions(
--question_type, question_level, question_text,
--ex1, ex2, ex3, answer)
--values
--('TEXT_SELECT', 'medium', 'In the winter, I wear a _______ to keep warm.',
--'T-shirt', 'jumper', 'hat', 2),
--('TEXT_SELECT', 'medium', 'We went to the _______ to watch a film.',
--'park', 'mall', 'cinema', 3),
--('TEXT_SELECT', 'medium', 'I wear my _______ when it rains.',
--'wellies', 'flip-flops', 'socks', 1),
--('TEXT_SELECT', 'medium', 'I need to buy some ________ for my new project.',
--'groceries', 'stationery', 'furniture', 2),
--('TEXT_SELECT', 'medium', 'In the garden, we have a lovely ________ full of flowers.',
--'patch', 'garden', 'lawn', 1),
--('TEXT_SELECT', 'medium', 'My favourite type of ________ is Italian.',
--'cuisine', 'dish', 'meal', 1),
--('TEXT_SELECT', 'medium', "For lunch, I had a hamburger and ________ at the McDonald's.",
--'roast', 'curry', 'chips', 3),
--('TEXT_SELECT', 'medium', 'I need to buy some ________ for my art project.',
--'tools', 'supplies', 'materials', 2),
--('TEXT_SELECT', 'medium', 'I need to buy new ________ for my school uniform.',
--'jeans', 'pants', 'shorts', 2),
--('TEXT_SELECT', 'medium', 'My favourite ________ is to listen to music.',
--'pastime', 'job', 'chore', 1),
--('TEXT_SELECT', 'medium', 'I like to watch a ________ on television.',
--'film', 'feature', 'programme', 3),
--('TEXT_SELECT', 'medium', 'I usually have a ________ with my lunch.',
--'cake', 'biscuit', 'fist', 2),
--('TEXT_SELECT', 'medium',  'My job is to drive ________.',
--'shopping cart', 'lorry', 'mobile', 2),
--('TEXT_SELECT', 'medium', "Let's order some ________ with burgers.",
--'fizzy drink', 'fish', 'grocery', 1),
--('TEXT_SELECT', 'medium', 'You can trash them into that ________.',
--'drawer', 'toilet', 'bin', 3);
--
---- IMAGE_SELECT (medium) 문제 insert
--insert into questions(
--question_type, question_level, question_file, question_text,
--ex1, ex2, ex3, answer)
--VALUES
--('IMAGE_SELECT', 'MEDIUM', 'Id_1.webp', '1',
--'He is wearing glasses.',
--'He is pointing at himself.',
--'There are glasses on his head.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_2.webp', '2',
--'There is a theatre in front of the car.',
--'The car is going to the petrol station.',
--'He is standing in front of the supermarket.', 2),
--('IMAGE_SELECT', 'MEDIUM', 'Id_3.jpg', '3',
--'She is throwing the dough.',
--'There are lots of snacks.',
--'She is eating a biscuit.', 3),
--('IMAGE_SELECT', 'MEDIUM', 'Id_4.jpg', '4',
--'There is a bin filled with rubbish.',
--'The bin is being emptied.',
--'There is a lot of rubbish near the box.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_5.jpg', '5',
--'The bulletin board is empty.',
--'The bulletin board is being removed.',
--'There is a schedule table on the bulletin board.', 3),
--('IMAGE_SELECT', 'MEDIUM', 'Id_6.webp', '6',
--'The vehicle is parked on the road.',
--'The vehicle is being repaired at the garage.',
--'The vehicle is a bicycle.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_7.jpg', '7',
--'They are playing football in the park.',
--'They are watching rugby on the television.',
--'They are playing basketball on the court.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_8.jpg', '8',
--'People are shopping in the mall.',
--'People are queuing at the cinema for tickets.',
--'People are eating at a restaurant.', 2),
--('IMAGE_SELECT', 'MEDIUM', 'Id_9.jpg', '9',
--'The sky is clear and blue.',
--'The sky is filled with clouds.',
--'The sky is grey and overcast.', 2),
--('IMAGE_SELECT', 'MEDIUM', 'Id_10.webp', '10',
--'He is writing in a journal.',
--'He is watching television.',
--'He is sitting in a comfy chair with a book.', 3),
--('IMAGE_SELECT', 'MEDIUM', 'Id_11.jpg', '11',
--'There is a toilet outside.',
--'There is a cafe outside.',
--'People are queuing in front of the ticket booth.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_12.jpg', '12',
--'The leaves are turning golden in autumn.',
--'The leaves are falling in spring.',
--'The leaves are blooming in summer.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_13.jpg', '13',
--'They are going to the cinema.',
--'They are playing games at home.',
--'They are browsing the stalls at the market.', 3),
--('IMAGE_SELECT', 'MEDIUM', 'Id_14.jpg', '14',
--'They are studying for exams.',
--'They are trekking through the hills together.',
--'They are watching television.', 2),
--('IMAGE_SELECT', 'MEDIUM', 'Id_15.jpg', '15',
--'They are playing football.',
--'They are swimming.',
--'They are reading books at home.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_16.jpg', '16',
--'They are decorating the tree with lights and baubles.',
--'They are preparing a picnic in the park.',
--'They are cleaning the house.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_17.jpg', '17',
--'They are playing chess indoors.',
--'They are riding their bicycles in the street.',
--'They are swimming in the pool.', 2),
--('IMAGE_SELECT', 'MEDIUM', 'Id_18.jpg', '18',
--'They are reading books at home.',
--'They are playing in the waves at the seaside.',
--'They are going to the cinema.', 2),
--('IMAGE_SELECT', 'MEDIUM', 'Id_19.jpg', '19',
--'There is a puddle on a rainy day.',
--'They are playing outside in the sun.',
--'There are books indoors.', 1),
--('IMAGE_SELECT', 'MEDIUM', 'Id_20.jpg', '20',
--'They are watching a film at the cinema.',
--'They are going for a hike.',
--'They are studying at home.', 1);
--
---- TTS_SELECT 유형 insert문
--insert into questions(
--question_type, question_level, question_text,
--ex1, ex2, ex3, answer)
--VALUES
--('TTS_SELECT', 'MEDIUM', '1',
--'They are going for a day out.',
--'They are staying at home.',
--'They are visiting relatives.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '2',
--'I need a check-up.',
--'I am here to buy some groceries.',
--'I am here to attend a meeting.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '3',
--'I feel fine.',
--'I have been feeling unwell for a few days.',
--'I am just tired.'
--, 2),
--('TTS_SELECT', 'MEDIUM', '4',
--'I feel great.',
--'I have no symptoms.',
--'I have a cough and a fever.'
--, 3),
--('TTS_SELECT', 'MEDIUM', '5',
--'I have been eating healthy food.',
--'Yes, I have taken some paracetamol.',
--'Yes, I took some vitamins.'
--, 2),
--('TTS_SELECT', 'MEDIUM', '6',
--'Yes, I had my flu jab.',
--'No, I don\’t like needles.',
--'I had a lovely meal.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '7',
--'I use a kettle.',
--'I use a microwave.',
--'I use a toaster.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '8',
--'Yes, my father has small eyes.',
--'No, we are all healthy.',
--'We enjoy family gatherings.'
--, 2),
--('TTS_SELECT', 'MEDIUM', '9',
--'I don’t know my allergy type.',
--'I prefer orange juice.',
--'I have type O.'
--, 3),
--('TTS_SELECT', 'MEDIUM', '10',
--'Yes, I take my allergy pills.',
--'I only drink tea.',
--'I don’t like chocolate.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '11',
--'I drive a taxi.',
--'I drive a lorry.',
--'I drive a bus.'
--, 2),
--('TTS_SELECT', 'MEDIUM', '12',
--'I wear sandals.',
--'I wear a sweater.',
--'I wear a raincoat.'
--, 3),
--('TTS_SELECT', 'MEDIUM', '13',
--'I go to a cobbler.',
--'I visit a doctor.',
--'I call my friend.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '14',
--'I often skip snacks.',
--'I love crisps.',
--'I’m not a fan of sweets.'
--, 2),
--('TTS_SELECT', 'MEDIUM', '15',
--'I am queuing to book a ticket',
--'He knows everything',
--'You should check them.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '16',
--'Sorry, I have no occupation.',
--'Sure, what is it?',
--"No, He did it for me this morning."
--, 2),
--('TTS_SELECT', 'MEDIUM', '17',
--'Sorry, My work is to busy.',
--'The machine is not working.',
--'I usually take a bus.'
--, 3),
--('TTS_SELECT', 'MEDIUM', '18',
--'I usually go see my grandfather.',
--'The advisor is not here now.',
--'Please turn off your mobile devices.'
--, 1),
--('TTS_SELECT', 'MEDIUM', '19',
--'Cream on a cup of tea.',
--'Thank you for doing me a favor.',
--'Strawberry is the best.'
--, 3),
--('TTS_SELECT', 'MEDIUM', '20',
--'No, thanks. I do not prefer sweets.',
--'Cook them into the oven.',
--'The savory one.'
--, 1);
