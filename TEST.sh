QuantityTest=19
QuantitPassedTests=0
declare -A CaseTest
CaseTest[0]=""
CaseTest[1]="-h"
CaseTest[2]="-login user -pass zzz"
CaseTest[3]="-pass zzz -login user"
CaseTest[4]="-login !user! -pass zzz"
CaseTest[5]="-login asd -pass dsa"
CaseTest[6]="-login admin -pass 0000"
CaseTest[7]="-login user -pass zzz -role READ -res A"
CaseTest[8]="-login user -pass zzz -role qwe -res A"
CaseTest[9]="-login q -pass ytrewq -role EXECUTE -res A"
CaseTest[10]="-login admin -pass 0000 -role READ -res A.B"
CaseTest[11]="-login admin -pass 0000 -role WRITE -res A.B.C"
CaseTest[12]="-login user -pass zzz -role qwe -res A.B.C"
CaseTest[13]="-login user -pass zzz -role WRITE -res A.B"
CaseTest[14]="-login user -pass zzz -role READ -res A.B -ds 2020-01-11 -de 2020-01-12 -vol 10"
CaseTest[15]="-login user -pass zzz -role READ -res A.B -ds 2020.01.11 -de 2020.01.12 -vol 10"
CaseTest[16]="-login user -pass zzz -role READ -res A.B -ds 2020-01-1 -de 2020-01-60 -vol 10"
CaseTest[17]="-login user -pass zzz -role READ -res A.B -ds 2020-01-11 -de 2020-10-12 -vol hgh"
CaseTest[18]="-login user -pass zzz -role WRITE -res A -ds 2020-01-12 -de 2020-01-13 -vol 10"

expectedExitCodes=(1 1 0 0 2 3 4 0 5 6 0 0 4 6 0 7 7 7 6)

for ((i = 0; i < "$QuantityTest"; i++)); do
  test=${CaseTest[$i]}
  expectedExitCode=${expectedExitCodes[$i]}
  ./RUN.sh ""${test}""
  exitCode="$?"
  if [ "$exitCode" == "$expectedExitCode" ]; then
    echo "CaseTest $i passed exit code $exitCode"
    let QuantitPassedTests++
  else
    echo "CaseTest $i error incoming $exitCode expected - $expectedExitCode."
  fi
done
echo "Result: $QuantitPassedTests passed tests"
if [ "$QuantitPassedTests" == "$QuantityTest" ]; then
  exit 0
else
  exit 1
fi
sleep 5m