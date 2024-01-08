Categorymasterquery = select Name  from Categorymaster where status = 1 order by sequence

FormFieldsquery = select Replace(fm.Name,''_'','' '') as formName, Replace(ffd.FieldName,''_'','' '') as FieldName,DataType,ControlType,Required from FormFieldDetail ffd \n" +
                              "join FormMaster fm on fm.FormID = ffd.FormID where fm.FormStatus = 1 and ffd.ForDEO = 1 and ffd.ForPM = {1} and  fm.Name  = {0} " +
                              "order by fm.FormSequence,ffd.Sequence

ProductColumnquery =               select FieldName as ProductColumn  from FormFieldDetail ffd \n" +
                                               "join FormMaster fm on fm.FormID = ffd.FormID where fm.FormStatus = 1 and ffd.ForDEO = 1 and ffd.ForPM = 1 and  fm.Name  = {0}

FormMasterquery =        select  Replace(Name,'_',' ') as FormName,IsQuestionForm from FormMaster where FormStatus = 1 order by FormSequence

Productquery = select {3} as ProductName from {0}master sm" +
                           " join {0}Relation sr on sr.{0}ID = sm.{0}ID \n" +
                           "join CategoryMaster cm on cm.CategoryID = sm.CategoryID" +
                           " where TargetID = {1} and name = {2}


EnumFieldquery =   select ffo.FormFieldOption as FieldOption from FormFieldOption ffo " +
                               "join FormFieldDetail ffd on ffd.FormFieldID = ffo.FormFieldID where Replace(FieldName,''_'','' '') ={0}


