select uuid, username, password, role
from auth_users
where username = ?;