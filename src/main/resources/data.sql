-- 1) カテゴリ投入（id は IDENTITY なので指定不要）
INSERT INTO product_category (category_uuid, name) VALUES
  (RANDOM_UUID(), '文房具'),
  (RANDOM_UUID(), '雑貨'),
  (RANDOM_UUID(), 'パソコン周辺機器');

-- 2) 商品投入（カテゴリ名→category_id を解決してINSERT）
INSERT INTO product (product_uuid, name, price, category_id)
SELECT
  CAST(v.uuid AS UUID) AS product_uuid,
  v.name,
  v.price,
  c.id AS category_id
FROM (
  -- 文房具
  SELECT 'ac413f22-0cf1-490a-9635-7e9ca810e544' AS uuid, '水性ボールペン(黒)' AS name, 120 AS price, '文房具' AS catname
  UNION ALL SELECT '8f81a72a-58ef-422b-b472-d982e8665292', '水性ボールペン(赤)', 120, '文房具'
  UNION ALL SELECT 'd952b98c-a1ea-478d-8380-3b90fde872ea', '水性ボールペン(青)', 120, '文房具'
  UNION ALL SELECT '9959e553-c9da-4646-bd85-8663a3541583', '油性ボールペン(黒)', 100, '文房具'
  UNION ALL SELECT '79023e82-9197-40a5-b236-26487f404be4', '油性ボールペン(赤)', 100, '文房具'
  UNION ALL SELECT '7dfd0fd0-0893-4d20-83ef-6f70aab0ab76', '油性ボールペン(青)', 100, '文房具'
  UNION ALL SELECT 'dc7243af-c2ce-4136-bd5d-c6b28ee0a20a', '蛍光ペン(黄)', 130, '文房具'
  UNION ALL SELECT '83fbc81d-2498-4da6-b8c2-54878d3b67ff', '蛍光ペン(赤)', 130, '文房具'
  UNION ALL SELECT 'ee4b3752-3fbd-45fc-afb5-8f37c3f701c9', '蛍光ペン(青)', 130, '文房具'
  UNION ALL SELECT '35cb51a7-df79-4771-9939-7f32c19bca45', '蛍光ペン(緑)', 130, '文房具'
  UNION ALL SELECT 'e4850253-f363-4e79-8110-7335e4af45be', '鉛筆(黒)', 100, '文房具'
  UNION ALL SELECT '5ca7dbdf-0010-44c5-a001-e4c13c4fe3a1', '鉛筆(赤)', 100, '文房具'
  UNION ALL SELECT 'fbc43b9b-90a9-4712-925c-4d66a2a30372', '色鉛筆(12色)', 400, '文房具'
  UNION ALL SELECT '4b3db238-8ada-49b4-bb60-1a034914e528', '色鉛筆(48色)', 1300, '文房具'
  UNION ALL SELECT '1a2b3c4d-0001-4000-8000-000000000001', '消しゴム', 150, '文房具'
  UNION ALL SELECT '1a2b3c4d-0002-4000-8000-000000000002', 'ノート', 200, '文房具'

  -- 雑貨
  UNION ALL SELECT 'debdbd8c-5b48-4b1a-9697-98ba321ddd40', 'レザーネックレス', 300, '雑貨'
  UNION ALL SELECT '367197c5-32bd-479a-9102-c601145464c4', 'ワンタッチ開閉傘', 3000, '雑貨'
  UNION ALL SELECT '657578d2-8820-4490-a6ec-06d9c7cccd0f', '金魚風呂敷', 500, '雑貨'
  UNION ALL SELECT '8c107894-4ebc-445b-9603-c9e8e6524f9d', '折畳トートバッグ', 600, '雑貨'
  UNION ALL SELECT '2f8e074c-d0b1-441b-9dd4-6cf0ec570ce6', 'アイマスク', 900, '雑貨'
  UNION ALL SELECT '2fb9fe48-3520-47ef-9e1a-338db7152884', '防水スプレー', 500, '雑貨'
  UNION ALL SELECT 'f536311a-b9de-4873-a603-70953a2261be', 'キーホルダ', 800, '雑貨'

  -- パソコン周辺機器
  UNION ALL SELECT '82014174-6785-4242-b307-a806fd1f8470', 'ワイヤレスマウス', 900, 'パソコン周辺機器'
  UNION ALL SELECT 'ddd1e5ae-fb90-4a47-bb87-c91b305c7444', 'ワイヤレストラックボール', 1300, 'パソコン周辺機器'
  UNION ALL SELECT 'aa5e07aa-06f9-4037-9755-e1de3c0ad4ac', '有線光学式マウス', 500, 'パソコン周辺機器'
  UNION ALL SELECT '53cfa873-c86b-48bd-a68c-458d7bb5c844', '光学式ゲーミングマウス', 4800, 'パソコン周辺機器'
  UNION ALL SELECT '376f7a75-cc99-4428-b35a-889bcb3c90af', '有線ゲーミングマウス', 3800, 'パソコン周辺機器'
  UNION ALL SELECT '38c6e236-90ca-48a2-b427-acb9d834b591', 'USB有線式キーボード', 1400, 'パソコン周辺機器'
  UNION ALL SELECT 'dc2e5a33-a2b7-4414-9a53-f9750e7da8ed', '無線式キーボード', 1900, 'パソコン周辺機器'
) v
JOIN product_category c
  ON c.name = v.catname;

-- 3) 在庫を一括投入（初期在庫 100）
INSERT INTO product_stock (stock_uuid, stock, product_id)
SELECT RANDOM_UUID(), 100, p.id
FROM product p;
