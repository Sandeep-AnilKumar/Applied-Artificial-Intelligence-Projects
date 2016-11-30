import pandas as pd
import numpy as np
from sklearn import metrics
from sklearn.model_selection import KFold
import xgboost
import time
from sklearn.feature_selection import SelectKBest
from sklearn.feature_selection import chi2


train_data = pd.read_csv('train.csv', nrows=5000)
#test_data = pd.read_csv('test.csv', nrows=100)

#print(train_data.head())

for i in range(1, 117):
    train_data['cat' + str(i)] = train_data['cat' + str(i)].astype('category')
#
# for i in range(1, 117):
#     test_data['cat' + str(i)] = test_data['cat' + str(i)].astype('category')

#print(train_data['cat1'].dtype)
#print(train_data['cat116'].dtype)


categorical_columns = train_data.select_dtypes(['category']).columns
# test_categorical_columns = test_data.select_dtypes(['category']).columns

#print(categorical_columns)

train_data[categorical_columns] = train_data[categorical_columns].apply(lambda x: x.cat.codes)
# test_data[categorical_columns] = test_data[categorical_columns].apply(lambda x: x.cat.codes)

#print(train_data.head())

train_data_vector = train_data.as_matrix(columns=train_data.columns[1:131]).astype(np.long)
#print(train_data_vector)

# test_data_vector = test_data.as_matrix(columns=test_data.columns[1:]).astype(np.long)

train_data_target = train_data['loss'].values.astype(np.long)
#
# reg = linear_model.LogisticRegression()
# reg = reg.fit(train_data_vector, train_data_target)
# lr_predict = reg.predict(test_data_vector)
#
# print(lr_predict)

kf = KFold(n_splits=10)
fold_number = 1

# print(train_data.shape)
mae_accuracies = list()
train_data_new = SelectKBest(chi2, k=100).fit_transform(train_data_vector, train_data_target)
# print(train_data_new.shape)
# print(train_data_new[1])

train_new_data_df = pd.DataFrame(train_data_new)

for train, test in kf.split(train_data):
    start_time = time.time()
    train_data_vector = [train_new_data_df.iloc[t, :].astype(np.long) for t in train]
    test_data_vector = [train_new_data_df.iloc[t, :].astype(np.long) for t in test]

    train_data_target = [train_data.iloc[t, 131].astype(np.long) for t in train]
    test_data_target = [train_data.iloc[t, 131].astype(np.long) for t in test]

    reg = xgboost.XGBRegressor(reg_alpha=0.2, n_estimators=1000, learning_rate=0.2)
    reg = reg.fit(train_data_vector, train_data_target)
    lr_predict = reg.predict(test_data_vector)
    lr_accuracy = metrics.mean_absolute_error(test_data_target, lr_predict)
    print("\nIn fold number", fold_number, "mae is", lr_accuracy)
    print("Time for fold", fold_number, "is:=", time.time() - start_time)
    fold_number += 1
    mae_accuracies.append(lr_accuracy)


avg = 0

for acc in mae_accuracies:
    avg += acc


print("Average mae", avg / 10)

