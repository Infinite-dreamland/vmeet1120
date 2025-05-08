import requests
import time
import hashlib

data = {
    'username': "未来最棒",
    'aid': 18
}

def set_token(data, password):
    # set expires = timestamp + 300. The timestamp is UTC timestamp in seconds.
    data['expires'] = int(time.time()) + 300
    # token = md5(username + expires + password)
    data['token'] = hashlib.md5((str(data['aid']) + (str(data['expires'])) + password + data['username']).encode()).hexdigest()

set_token(data, 'JianzaoJianzao0')
print(requests.get(f'https://vmeet.fun/backend/getCharacterConfig', params=data, verify=False).text)

data = {
    'username': "未来最棒",
    'aid': 18,
    'config': '{}'
}

def set_token(data, password):
    # set expires = timestamp + 300. The timestamp is UTC timestamp in seconds.
    data['expires'] = int(time.time()) + 300
    # token = md5(username + expires + password)
    data['token'] = hashlib.md5((str(data['aid']) + data['config'] + (str(data['expires'])) + password + data['username']).encode()).hexdigest()

set_token(data, 'JianzaoJianzao0')
print(requests.post(f'https://vmeet.fun/backend/updateCharacterConfig', data=data, verify=False).text)
