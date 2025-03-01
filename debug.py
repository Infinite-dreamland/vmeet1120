import requests
import time
import hashlib

data = {
    'username': '13233131808',
}

def set_token(data, password):
    # set expires = timestamp + 300. The timestamp is UTC timestamp in seconds.
    data['expires'] = int(time.time()) + 300
    # token = md5(username + expires + password)
    data['token'] = hashlib.md5((data['username'] + str(data['expires']) + password).encode()).hexdigest()

set_token(data, 'xyl3331996')
print(requests.get(f'https://vmeet.fun/backend/getUserInfo', params=data, verify=False).text)
