select 1
from meter_readings
where user_uuid = ?
  and period = ?
  and meter_uuid = ?