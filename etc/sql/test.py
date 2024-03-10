#python3
import requests
from time import sleep
import random
import json

# robots.txt 확인함

# 255276635
POSTID = 0
CATEGORY_IDS = (0, 11, 12, 13, 21, 22, 23, 31, 32, 33, 141, 42, 43)

POST_SQL = """
    INSERT INTO POST (ID, MEMBER_ID, CATEGORY_ID, TITLE, CONTENT, STATUS, PRICE, IS_DELETED, MODIFIED_AT)
    VALUES ({POSTID},{member_id}, {category_id}, '{title}', '{content}', "ON_SALE", {price}, false, now());
    """
POST_IMAGE_SQL = """
    INSERT INTO POST_IMAGE (ID,POST_ID,IMAGE_PATH,IS_THUMBNAIL) VALUES
    ({ID},{ID},'/api/file/static/{ID}.jpeg',true)
    """

HASHTAG_SQL = """
INSERT INTO HASHTAG (ID,TAG_NAME,CHOSUNG) VALUES
{};
"""

POST_HASHTAG_SQL = """
INSERT INTO POST_HASHTAG (POST_ID, HASHTAG_ID)
VALUES{} ;
"""


HASHTAG_DICT = dict()


def get_post(url):
    global POSTID
    POSTID += 1

    res = requests.get(url)
    data = res.json()
    product = data["data"]["product"]

    title = product["name"].replace("'","\\'")
    content = product["description"].replace("'","\\'")
    price = product["price"]

    hashtag_list = product["keywords"]

    member_id = random.randint(1, 11)
    category_id = random.choice(CATEGORY_IDS)

    sql = POST_SQL.format(
        POSTID=POSTID,
        member_id=member_id,
        category_id=category_id,
        title=title,
        content=content,
        price=price,
    )
    with open ('./sqlstore/POST.sql','a') as f:
        f.write(sql)
    image_url = product["imageUrl"]
    get_POST_IMAGE(image_url, POSTID)
    get_hashtag(POSTID, hashtag_list)


def get_POST_IMAGE(image_url, ID):
    content = requests.get(image_url).content
    ext = image_url.split(".")[-1]
    with open(f"./imgstore/{ID}.{ext}", "wb") as f:
        f.write(content)

    sql = POST_IMAGE_SQL.format(ID=ID)
    with open(f"./sqlstore/POST_IMAGE.sql","a") as f:
        f.write(sql);


def get_hashtag(postid: int, hashtag_list: list = []) -> None:
    for hashtag in hashtag_list:
        if hashtag not in HASHTAG_DICT:
            HASHTAG_DICT[hashtag] = len(HASHTAG_DICT)
            chosung = get_chosung_string(hashtag)
            params = (len(HASHTAG_DICT)+1, hashtag, chosung)
            sql = HASHTAG_SQL.format(params)
            with open(f"./sqlstore/HASHTAG.sql","a") as f:
                f.write(sql);

        
        hashtagId = HASHTAG_DICT[hashtag]
        ph_params = (postid,hashtagId)
        ph_sql = POST_HASHTAG_SQL.format(ph_params)
        with open(f"./sqlstore/POST_HASHTAG.sql","a") as f:
            f.write(ph_sql);



def get_chosung_string(s):
    def get_chosung(c):
        if not ("가" <= c <= "힣"):
            return c  # 음절이 아니라면

        # 한글 유니코드 = [(초성 인덱스)×588 + (중성 인덱스)×28 + (종성 인덱스)] + 44032
        CHOSUNG = [
            "ㄱ",
            "ㄲ",
            "ㄴ",
            "ㄷ",
            "ㄸ",
            "ㄹ",
            "ㅁ",
            "ㅂ",
            "ㅃ",
            "ㅅ",
            "ㅆ",
            "ㅇ",
            "ㅈ",
            "ㅉ",
            "ㅊ",
            "ㅋ",
            "ㅌ",
            "ㅍ",
            "ㅎ",
        ]

        # 젤첫문자와의 거리를 구하고, 모든 경우의 수(중성*종성)로 인덱스를 구함
        idx = (ord(c) - ord("가")) // (21 * 28)
        return CHOSUNG[idx]

    return "".join(get_chosung(c) for c in s)


if __name__ == "__main__":
    URL = "https://api.bunjang.co.kr/api/pms/v2/products-detail/{num}?viewerUid=-1"
    all_cnt=0
    success_cnt = 0
    fail_cnt = 0

    for i in range(255276635-5000,255276635):
        try:
            get_post(URL.format(num=i))
            success_cnt+=1
        except Exception as e:
            print(f'failed...,{e}')
            fail_cnt +=1
        finally:
            print(f'all_cnt = {all_cnt}, success_cnt = {success_cnt} / fail_cnt = {fail_cnt} ')
            sleep(0.5)
            

