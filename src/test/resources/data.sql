INSERT INTO users (
    pk, name, id, password, gender, skin_type, skin_worries
)
VALUES
    (1, 'name', 'duplicateId', 'userPassword', '여성', '중성', '해당없음'),
    (2, 'name', 'userId', 'userPassword', '남성', '중성', '해당없음');

INSERT INTO cosmetic (pk, name, brand, image_url, price, cosmetic_types, rating, grade, skin_type, skin_worries)
VALUES
    (2, '별점 5', 'Brand A', 'http://example.com/image1.jpg', 50000, '앰플,토너', 5, '0', '건성', '아토피,아토피'),
    (1, '별점 4', 'Brand B', 'http://example.com/image2.jpg', 40000, '에센스,세럼', 4, '1', '지성', '아토피,아토피'),
    (3, '별점 3', 'Brand C', 'http://example.com/image3.jpg', 30000, '크림,마스크팩', 3, '1', '복합성', '아토피,아토피'),
    (5, '검색 2', 'Brand D', 'http://example.com/image4.jpg', 20000, '토너,에센스', 2, '2', '지성', '아토피,아토피'),
    (4, '검색 1', 'Brand E', 'http://example.com/image5.jpg', 10000, '앰플,크림', 1, '0', '건성', '아토피,아토피');

-- IngredientPk에 대한 데이터는 별도의 테이블인 cosmetic_ingredients에 삽입합니다.
INSERT INTO cosmetic_ingredients (cosmetic_pk, ingredient_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 5),
    (3, 6),
    (4, 7),
    (4, 8),
    (5, 9),
    (5, 10);

INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (1, '정제수', 1);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (2, '글리세린', 2);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (3, '부틸렌글라이콜', 3);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (4, '소듐하이알루로네이트', 4);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (5, '판테놀', 5);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (6, '나이아신아마이드', 6);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (7, '페녹시에탄올', 7);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (8, '향료', 8);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (9, '에탄올', 9);
INSERT INTO ingredient (ingredient_id, name, ewg_grade) VALUES (10, '파라핀', 10);