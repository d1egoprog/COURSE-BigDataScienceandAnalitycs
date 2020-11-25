import nltk
from nltk.tokenize import word_tokenize 
from nltk.corpus import stopwords 
import io 

import pandas as pd
df = pd.read_csv('C:\\temp\\corewoman\\export.dsv', encoding='utf-8', sep=r'\|\|\|', engine='python')
df = df['status']
s = ' '.join(df)
s = re.sub(r'\w+:\/{2}[\d\w-]+(\.[\d\w-]+)*(?:(?:\/[^\s/]*))*', '', s)

nltk.download('stopwords')
stop = set(stopwords.words('spanish')) 

nltk.download('punkt')
tokenizer = RegexpTokenizer(r'\w+')
tokens = tokenizer.tokenize(s)
withoutStop = [word for word in tokens if word not in stop and word != 'RT']

bof = pd.Series(withoutStop).value_counts()
print(bof)
