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
