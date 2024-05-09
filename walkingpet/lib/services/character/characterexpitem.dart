import 'dart:convert';

import 'package:walkingpet/services/Interceptor.dart';

const String baseUrl = "https://walkingpet.co.kr";
const String expitemUrl = "character/item/use/expitem";

// 캐릭터별 경험치 아이템 사용

Future<Map<String, dynamic>> Expitem(int quantity) async {
  final client = AuthInterceptor();
  final url = Uri.parse('$baseUrl/$expitemUrl?quantity=$quantity');
  final response = await client.get(url);

  if (response.statusCode == 200) {
    var data = utf8.decode(response.bodyBytes);
    return jsonDecode(data);
  } else {
    print("캐릭터별 경험치 아이템 사용 오류 : ${response.statusCode}");
    throw Error();
  }
}
