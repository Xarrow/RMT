# -*- coding:utf-8 -*-*
import os
import sys

#  ================= env function======================
# add other modules to extend
# PY Extension
PY_EMBED_NAME = "python-3.9.0-embed-amd64"
PY_VENDOR_NAME = "vendor"
PARENT_DIR = os.path.abspath(os.path.dirname(__file__))
VENDOR_DIR = os.path.join(PARENT_DIR, PY_VENDOR_NAME)
sys.path.append(VENDOR_DIR)

print(VENDOR_DIR)

import requests
print(requests.get(url='https://twitter.com').text)

# import you_get
# print(dir(you_get))
# from you_get.extractors import (
#     imgur,
#     magisto,
#     youtube,
#     missevan,
#     acfun,
#     bilibili,
#     soundcloud,
#     tiktok
# )
#
# bilibili.download(
#     "https://www.bilibili.com/video/BV1sL4y177sC", info_only=True
# )
