
from google.cloud import storage



storage_client = storage.Client()
bucket_name = 'group8'
bucket = storage_client.create_bucket(bucket_name)
print('Bucket {} created'.format(bucket.name))

