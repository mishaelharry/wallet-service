# wallet-service

Register User:
Url: http://localhost:9006/wallet-service/api/v1/users/register
Request:
{
    "firstName":"Mishael",
    "lastName":"Harry",
    "mobile":"09039639237",
    "email":"harry4sure@hotmail.com",
    "pin":"Test124",
    "country":"NG"
}

Update BVN KYC:
Url: http://localhost:9006/wallet-service/api/v1/users/update-bvn/1252
Request:
{
    "bvn":"12345678900"
}

Update Identity KYC:
Url: http://localhost:9006/wallet-service/api/v1/users/update-identity/1252
Request:
{
    "identityType":"Driver Licence",
    "identityNumber":"393939393993"
}

Get Wallet:
Url: http://localhost:9006/wallet-service/api/v1/wallets/153848907


Transfer:
Url: http://localhost:9006/wallet-service/api/v1/wallets/transfer
Request:
{
    "sourceAccount":"219916057",
    "destinationAccount":"153848907",
    "amount": 150000,
    "pin":"Test124",
    "narration":"test"
}

Topup:
Url: http://localhost:9006/wallet-service/api/v1/wallets/topup
Request:
{
    "accountNumber":"219916057",
    "amount":100000
}
