import requests
import json
import time
import hashlib

data = {
    'username': 'xyc',
    'chatId': 1,
}

def set_token(data, password):
    # set expires = timestamp + 300. The timestamp is UTC timestamp in seconds.
    data['expires'] = int(time.time()) + 300
    # token = md5(username + expires + password)
    data['token'] = hashlib.md5((str(data['chatId']) + data['username'] + str(data['expires']) + password).encode()).hexdigest()

set_token(data, 'xyl3331996')
# print(requests.get('https://vmeet.fun/backend/getFriendship', params=data, verify=False).text)
print(requests.post('https://127.0.0.1:442/backend/deleteChat', data=data, verify=False).text)
