# -*- coding:utf-8 -*-*
import os
import sys

#  ================= env function======================
# add other modules to extend
# PY Extension
PY_EMBED_NAME = "python-3.9.0-embed-amd64"
PY_VENDOR_NAME = "vendor"
PARENT_DIR = os.path.abspath(os.path.dirname(__file__))
VENDOR_DIR = os.path.join(os.path.join(PARENT_DIR, PY_EMBED_NAME),
                          PY_VENDOR_NAME)
sys.path.append(VENDOR_DIR)

import requests

print(VENDOR_DIR)
print(requests.get(url='https://baidu.com').text)
from you_get.extractors import (
  imgur,
  magisto,
  youtube,
  missevan,
  acfun,
  bilibili,
  soundcloud,
  tiktok
)

# bilibili.download(
#     "https://www.bilibili.com/watchlater/#/av74906671/p6", info_only=True
# )

