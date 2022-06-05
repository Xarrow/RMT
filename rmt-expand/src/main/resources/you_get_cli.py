# -*- coding:utf-8 -*-

"""
 Author: helixcs
 Site: https://zeit.fun
 File: libs_loader.py.py
 Time: 2020/10/16
"""
import os
import sys

#  ================= env function======================
# add other modules to extend
# PY Extension
PY_VENDOR_NAME = "vendor"
PARENT_DIR = os.path.abspath(os.path.dirname(__file__))
VENDOR_DIR = os.path.join(PARENT_DIR, PY_VENDOR_NAME)

if VENDOR_DIR not in sys.path:
  sys.path.append(VENDOR_DIR)

import you_get

if __name__ == '__main__':
  you_get.main()
