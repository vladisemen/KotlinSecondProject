# Не работает. Нужно дописать программу
QuantityTest=14
QuantitPassedTests=0
declare -A test
test[0]="First.jar"
test[1]="First.jar -h"
test[2]="First.jar -login user -pass zzz"
test[3]="First.jar -pass user -login zzz"
test[4]="First.jar -login user@. -pass zzz"
test[5]="First.jar -login user -pass user"
test[6]="First.jar -login admin -pass zzz"
test[7]="First.jar -login user -pass zzz -role READ -res A"
test[8]="First.jar -login user -pass zzz -role -res A"
test[9]="First.jar -login q -pass ytrewq -role EXECUTE -res A"
test[10]="First.jar -login admin -pass admin -role READ -res A.B"
test[11]="First.jar -login admin -pass admin -role WRITE -res A.B.C"
test[12]="First.jar -login user -pass zzz -role qwe -res A.B.C"
test[13]="First.jar -login user -pass zzz -role WRITE -res A.B"


ExpectedExitCode=(1 1 0 0 2 3 4 0 5 6 0 0 4 6 0)
for ((i=0; i < "$QuantityTest"; i++))
do
  java -jar First.jar "$test[$i]"
  ExitCode="$?"
  if [ "$ExitCode" == "${ExpectedExitCode[$i]}" ]
   then
      echo "Test $i passed: exit code - $?."
      let QuantitPassedTest++
    else
      echo "Test $i failed: received - $ExitCode expected - $ExpectedExitCode."
  fi
done
echo "Result: $QuantitPassedTests passed tests"
if [ "$QuantitPassedTests" == "$QuantityTest" ]
  then
    return 0
  else
    return 1
fi
sleep 5m