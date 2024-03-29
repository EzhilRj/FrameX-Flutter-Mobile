Categorymasterquery = SELECT Name FROM CategoryMaster WHERE status = 1 ORDER BY Sequence;

FormFieldsquery = select Replace(fm.Name,''_'','' '') as formName, Replace(ffd.FieldName,''_'','' '') as FieldName,DataType,ControlType,Required from FormFieldDetail ffd join FormMaster fm on fm.FormID = ffd.FormID where fm.FormStatus = 1 and ffd.ForDEO = {2} and ffd.ForPM = {1} and  fm.Name  = {0} order by fm.FormSequence,ffd.Sequence

QuestionFormFieldsquery = Select Replace({2},''_'','' '') as formName,m.QuestionMode as FieldName, m.Qmodedatatype as DataType,m.QuestionMode as ControlType,QuestionRequired as Required   from {0}Master m  Where m.status=1 and Question = Replace({1},'' *'','''')

ProductColumnquery =   select FieldName as ProductColumn  from FormFieldDetail ffd  join FormMaster fm on fm.FormID = ffd.FormID where fm.FormStatus = 1 and ffd.ForDEO = 1 and ffd.ForPM = 1 and  fm.Name  = {0}

FormMasterquery = SELECT REPLACE(Name, '_', ' ') AS FormName, IsQuestionForm FROM FormMaster WHERE FormStatus = 1 ORDER BY FormSequence

Productquery = select {3}  as ProductName  from {0}master sm  join {0}Relation sr on sr.{0}ID = sm.{0}ID join CategoryMaster cm on cm.CategoryID = sm.CategoryID where TargetID = {1} and name = {2} order by sm.Sequence

EnumFieldquery= select ffo.FormFieldOption as FieldOption from FormFieldOption ffo join FormFieldDetail ffd on ffd.FormFieldID = ffo.FormFieldID join FormMaster fm on fm.FormID = ffd.FormID join {0}Master fmq on fmq.{0}ID = ffo.FQuestionID where fm.Name = {2} and fmq.Question = {1}