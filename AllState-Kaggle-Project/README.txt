I have used xgboost's XGBRegressor (XGBR), Please follow these instructions and install the same. 

https://www.kaggle.com/c/liberty-mutual-group-property-inspection-prediction/forums/t/15742/clear-instructions-to-install-xgboost-in-python

INSTRUCTIONS: -
    Open Terminal
    type : git clone https://github.com/dmlc/xgboost.git
    type : cd xgboost; make; cd wrapper; python.py setup install --user

These steps help me out. Just a little update of step 3 which work for me: type cd xgboost-master; make; cd python-package; python setup.py install

I am using UBUNTU OS, so this worked for me. If this does not work or if you are using any other OS, then go to this URL and install it.
http://xgboost.readthedocs.io/en/latest/build.html#installation-guide

However, if you do not want to do that, then I have also given code for skelarn's Gradient Boosting Regressor (GBR). Please install numpy, pandas, sklearn for the python code to run.
If you go ahead with using the sklearn's GBR and not XGBR, then uncomment the following lines from allstate.py file.

line no. 36 and 37

# reg = gBR()
# reg = reg.fit(train_data_vector, train_data_target)

and comment the lines 33 and 34. 

The same is true for lines 73 and 74 (uncomment) and 76 and 77 (comment).

I tried GBR first, with default parameters and got the following output after running the allstate.py.

Time for encoding training data is := 32.39
Time for reading test and train vectors and targets from the subset in the training data is := 0.67
Time for the regressor to train and predict on the training data subset is := 181.87
done with calculating the MAE for the subset of training data
Time for encoding testing data is := 24.19
Time for the regressor to train and predict is := 182.90
done with predicting loss values for the test data

Output files: - The files for training and testing subset cross validation for MAE is in : - output_file_gbr_default.csv
And for testing data loss prediction, the file is : - submissions_gbr_default.csv

I later tried XGBoost's XGBR with the parameters as: -
reg_alpha=0.2, n_estimators=1000, learning_rate=0.2

Params: -
learning_rate : learning rate shrinks the contribution of each tree by learning_rate. There is a trade-off between learning_rate and n_estimators.
n_estimators : The number of boosting stages to perform. Gradient boosting is fairly robust to over-fitting so a large number usually results in better performance.
reg_alpha : L1 regularization term on weights

The output obtained was: -

Time for encoding training data is := 37.50
Time for reading test and train vectors and targets from the subset in the training data is := 0.69
Time for the regressor to train and predict on the training data subset is := 495.79
done with calculating the MAE for the subset of training data
Time for encoding testing data is := 24.36
Time for the regressor to train and predict is := 505.65
done with predicting loss values for the test data

Output files: - The files for training and testing subset cross validation for MAE is in : - output_file_xgboost.csv
And for testing data loss prediction, the file is : - submissions_xgboost.csv

Then I tried with the GBR again with parameters as: -
n_estimators=1000, learning_rate=0.2, loss = 'ls'

The output obtained was: -
Time for encoding training data is := 35.83
Time for reading test and train vectors and targets from the subset in the training data is := 0.78
Time for the regressor to train and predict on the training data subset is := 1673.62
done with calculating the MAE for the subset of training data
Time for encoding testing data is := 24.56
Time for the regressor to train and predict is := 1920.22
done with predicting loss values for the test data

Output files: - The files for training and testing subset cross validation for MAE is in : - output_file_gbr_params.csv
And for testing data loss prediction, the file is : - submissions_gbr_params.csv

I have also attached a allstate.ipynb notebook, you can run that too if you have PyCharm and Jupyter. Please install PyCharm and then integrate JuPyter to the Project Interpreter. Do let me know if you need any additional help. The notebook has the code for xgboost regressor, since that gave the lowest mae.

NOTE: - I have used test subset for split as 0.1, and the MAE values for each of the runs will be slightly different than the ones in the file because the splitting is done randomly. But still it will be near to the values given in the files. The output_file_*.csv contains the MAE values for all the regressor runs.

The XGBOOST run gave the lowest MAE of 1204.23

REFERENCES: -
https://www.analyticsvidhya.com/blog/2016/02/complete-guide-parameter-tuning-gradient-boosting-gbm-python/

