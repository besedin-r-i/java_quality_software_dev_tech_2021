#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
import sys
from dostoevsky.tokenization import RegexTokenizer
from dostoevsky.models import FastTextSocialNetworkModel

tokenizer = RegexTokenizer()

model = FastTextSocialNetworkModel(tokenizer=tokenizer)

messages = [
    str(sys.argv[1])
]

results = model.predict(messages, k=3)

print(json.dumps(results[0]))