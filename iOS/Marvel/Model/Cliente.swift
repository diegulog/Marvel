//
//  Cliente.swift
//  Marvel
//
//  Created by Diego on 15/11/2020.
//

import Foundation
import CommonCrypto


class Cliente {
    
    static let base = "https://gateway.marvel.com/v1/public/"
    static let publicKey = "d86e70f5110515da2b5ea7f48d347f58"
    static let privateKey = "1287b638d82c4ad5409077e5eff5333d67d97424"
    static let ts = "diegulog"
    
    enum Endpoints {
        
        case characterById(Int)
        case characters(Int, Int)
        
        var stringValue: String {
            switch self {
            case .characters(let limit, let offset): return Cliente.base + "characters?limit=\(limit)&offset=\(offset)"
            case .characterById(let id): return "\(Cliente.base)characters/\(id)"
            }
        }
        
        var url: URL {
            var urlComponents = URLComponents(url: URL(string: stringValue)!, resolvingAgainstBaseURL: false)
            let hash = "\(ts)\(privateKey)\(publicKey)".md5
            var queryItems = [URLQueryItem(name: "apikey", value: Cliente.publicKey),
                              URLQueryItem(name: "ts", value: Cliente.ts),
                              URLQueryItem(name: "hash", value: hash)]
            if let tempQueryItems = urlComponents?.queryItems {
                queryItems.append(contentsOf: tempQueryItems)
            }
            urlComponents?.queryItems = queryItems
            urlComponents?.scheme = "https"
            
            return (urlComponents?.url!)!
        }
    }
    
    class func getCharacterById( id: Int , completion: @escaping (Character?, Error?) -> Void) {
        taskForGETRequest(url: Endpoints.characterById(id).url, responseType: MarvelResponse.self) { response, error in
            if let response = response{
                completion(response.data.results.first, nil)
            }else{
                completion(nil, error)
            }
        }
    }
    
    class func getCharacteres(limit :Int, offset :Int, completion: @escaping (DataResponse?, Error?) -> Void) {
        taskForGETRequest(url: Endpoints.characters(limit, offset).url, responseType: MarvelResponse.self) { response, error in
            if let response = response{
                completion(response.data, nil)
            }else{
                completion(nil, error)
            }
        }
    }
    
    
    class func taskForGETRequest<ResponseType: Decodable>(url: URL, responseType: ResponseType.Type, completion: @escaping (ResponseType?, Error?) -> Void) {
        print(url)
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard let data = data else {
                DispatchQueue.main.async {
                    completion(nil, error)
                }
                return
            }
            let decoder = JSONDecoder()
            do{
                let responseObject = try decoder.decode(ResponseType.self, from: data)
                DispatchQueue.main.async {
                    completion(responseObject, nil)
                }
            } catch {
                DispatchQueue.main.async {
                    completion(nil, error)
                }
            }
        }.resume()
    }
}


extension String {
    var md5: String {
        let data = Data(self.utf8)
        let hash = data.withUnsafeBytes { (bytes: UnsafeRawBufferPointer) -> [UInt8] in
            var hash = [UInt8](repeating: 0, count: Int(CC_MD5_DIGEST_LENGTH))
            CC_MD5(bytes.baseAddress, CC_LONG(data.count), &hash)
            return hash
        }
        return hash.map { String(format: "%02x", $0) }.joined()
    }
}
