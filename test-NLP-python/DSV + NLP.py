#!/usr/bin/env python
# coding: utf-8

# In[ ]:


greet = "Bienvenido a Python y a Tecnicas de NLP."
print(greet)


# In[ ]:


import pandas as pd

df = pd.read_csv('C:\\temp\\corewoman\\export-2020-7-23-16-9-54.dsv', encoding='utf-8', sep=r'\|\|\|', engine='python')
print(df)


# In[ ]:


df = df['status']
print(df)


# In[ ]:


s = ' '.join(df)
print(s)


# In[ ]:


import nltk
from nltk.corpus import stopwords
nltk.download('stopwords')
stop = set(stopwords.words('spanish')) 
print(stop)


# In[ ]:


import re
s = re.sub(r'\w+:\/{2}[\d\w-]+(\.[\d\w-]+)*(?:(?:\/[^\s/]*))*', '', s)
print(s)


# In[ ]:


from nltk.tokenize import word_tokenize 
nltk.download('punkt')
tokenGreet = word_tokenize(greet)
print(tokenGreet)


# In[ ]:


import nltk
from nltk.tokenize import word_tokenize 
nltk.download('punkt')

tokenGreet = word_tokenize(greet)
tokenGreetwithOut = []
#also - tokenGreetwithOut = [word for word in tokenGreet if word.isalnum()]
for word in tokenGreet:
    if word.isalnum():
        tokenGreetwithOut.append(word)
print(tokenGreetwithOut)


# In[ ]:


from nltk.tokenize import RegexpTokenizer
nltk.download('punkt')

# Good Regex - Reference https://www3.ntu.edu.sg/home/ehchua/programming/howto/Regexe.html#zz-1.1
tokenizer = RegexpTokenizer(r'\w+')
tokenGreet = tokenizer.tokenize(greet)

print(tokenGreet)


# In[ ]:


from nltk.tokenize import word_tokenize 
nltk.download('punkt')

tokens = tokenizer.tokenize(s)
withoutStop = [] 

#also - withoutStop = [word for word in tokens if w not in stop]
for w in tokens: 
    if w not in stop: 
        withoutStop.append(w) 
        
print(withoutStop)


# In[ ]:


withoutStop = [] 
#also - withoutStop = [word for word in tokens if w not in stop  and w != 'RT']
for w in tokens: 
    if w not in stop and w != 'RT' and w != '@': 
        withoutStop.append(w) 
        
print(withoutStop)


# In[ ]:


from nltk.tokenize import word_tokenize 
nltk.download('punkt')
tokens = tokenizer.tokenize(s)
withoutStop = [word for word in tokens if word not in stop and word != 'RT']
print(withoutStop)


# In[ ]:


bof = pd.Series(withoutStop).value_counts()
print(bof)


# In[ ]:


from nltk.stem.snowball import SnowballStemmer

stemmer = SnowballStemmer("spanish")
stemmed_spanish = [stemmer.stem(item) for item in withoutStop]
print(stemmed_spanish)


# In[ ]:


from sklearn.decomposition import LatentDirichletAllocation as LDA
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
import numpy as np



# Helper function
def print_topics(model, count_vectorizer, n_top_words):
    words = count_vectorizer.get_feature_names()
    for topic_idx, topic in enumerate(model.components_):
        print("\nTopic #%d:" % topic_idx)
        print(" ".join([words[i]
                        for i in topic.argsort()[:-n_top_words - 1:-1]]))


number_topics = 5
number_words = 10

listofstatuses = df.to_numpy()
count_vectorizer = CountVectorizer(stop_words=stop)
listofConversations = count_vectorizer.fit_transform(listofstatuses)

lda = LDA(n_components=number_topics)
lda.fit(listofConversations)

print("Topics found via LDA:")
print_topics(lda, count_vectorizer, number_words)


# In[ ]:


from wordcloud import WordCloud

wordcloud = WordCloud(background_color="white", max_words=5000, contour_width=3, contour_color='steelblue')
text = ','.join(withoutStop)
wordcloud.generate(text)
wordcloud.to_image()


# In[ ]:


plt = bof[:10].plot(kind="barh", title="10 Most Common Words") 


# In[ ]:




